package vk.itmo.teamgray.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
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
        return userRepository.findByVkId(vkId).orElseThrow(() -> DataNotFoundException.entity(User.class, vkId));
    }

    public UserDto getByVkId(Long vkId) {
        return userMapper.toDto(findByVkId(vkId));
    }

    public User getAuthUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null){
            throw new AuthenticationServiceException("Authentication required");
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }

    public UserDto findById(Long id) {
        return userMapper.toDto(findEntityById(id));
    }

    public UserDto createUser(UserCreateDto data) {
        return userMapper.toDto(
            userRepository.save(new User(data))
        );
    }

    public UserDto updateUser(UserUpdateDto updateDto) {
        var user = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getEmail(), user::setEmail);
        updated |= updateIfPresent(updateDto.getPhoneNumber(), user::setPhoneNumber);
        updated |= updateIfPresent(updateDto.getDateOfBirth(), user::setDateOfBirth);
        updated |= updateIfPresent(updateDto.getCityName(), user::setCityName);

        if (updated) {
            user = userRepository.save(user);
        }

        return userMapper.toDto(user);
    }
}
