package vk.itmo.teamgray.backend.user.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.user.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}