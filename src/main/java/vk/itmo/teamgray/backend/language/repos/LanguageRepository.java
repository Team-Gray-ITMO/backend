package vk.itmo.teamgray.backend.language.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.language.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}