package vk.itmo.teamgray.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataConflictException;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.user.dto.UserBaseDto;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;
import vk.itmo.teamgray.backend.user.dto.UserUpdateDto;
import vk.itmo.teamgray.backend.user.entities.User;
import vk.itmo.teamgray.backend.user.mapper.UserMapper;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends BaseService<User> {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public User findEntityById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(User.class, id));
    }

    public User findByVkId(Long vkId) {
        return userRepository.findByVkId(vkId)
            .orElseThrow(() -> DataNotFoundException.entity(User.class, vkId));
    }

    public UserBaseDto getByVkId(Long vkId) {
        return userMapper.toBaseDto(findByVkId(vkId));
    }

    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new AuthenticationServiceException("Authentication required");
        }

        String email = auth.getName();

        return userRepository.findByEmail(email)
            .orElseThrow(IllegalArgumentException::new);
    }

    public UserDto createUser(UserCreateDto createDto) {
        var user = new User();

        userRepository.findByVkId(createDto.getVkId())
            .ifPresent(existingUser -> {
                throw DataConflictException.unique(User.class, "VK ID", String.valueOf(createDto.getVkId()), existingUser.getId());
            });

        userRepository.findByEmail(createDto.getEmail())
            .ifPresent(existingUser -> {
                throw DataConflictException.unique(User.class, "Email", createDto.getEmail(), existingUser.getId());
            });

        user.setVkId(createDto.getVkId());
        user.setEmail(createDto.getEmail());
        user.setPhoneNumber(createDto.getPhoneNumber());
        user.setDateOfBirth(createDto.getDateOfBirth());
        user.setCityName(createDto.getCityName());
        user.setPreferredContact(createDto.getPreferredContact());

        return userMapper.toDto(
            userRepository.save(user)
        );
    }

    public UserDto updateUser(UserUpdateDto updateDto) {
        var user = getAuthUser();

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getEmail(), user::setEmail);
        updated |= updateIfPresent(updateDto.getPhoneNumber(), user::setPhoneNumber);
        updated |= updateIfPresent(updateDto.getDateOfBirth(), user::setDateOfBirth);
        updated |= updateIfPresent(updateDto.getCityName(), user::setCityName);
        updated |= updateIfPresent(updateDto.getPreferredContact(), user::setPreferredContact);

        if (updated) {
            user = userRepository.save(user);
        }

        return userMapper.toDto(user);
    }
}
