package vk.itmo.teamgray.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;
import vk.itmo.teamgray.backend.user.entities.User;
import vk.itmo.teamgray.backend.user.mapper.UserMapper;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
//TODO Integrate with Auth
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public UserDto findById(Long id) {
        return userMapper.toDto(findEntityById(id));
    }

    public UserDto createUser(UserCreateDto data) {
        return userMapper.toDto(
            userRepository.save(new User(data))
        );
    }
}
