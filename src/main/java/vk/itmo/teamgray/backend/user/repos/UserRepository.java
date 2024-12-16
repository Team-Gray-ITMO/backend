package vk.itmo.teamgray.backend.user.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.user.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVkId(Long vkId);

    Optional<User> findByEmail(String email);
}