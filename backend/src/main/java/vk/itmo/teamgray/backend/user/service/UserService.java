package vk.itmo.teamgray.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.entities.User;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@Service
@RequiredArgsConstructor
//TODO Integrate with Auth
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public User createUser(UserCreateDto data) {
        return userRepository.save(new User(data));
    }
}
