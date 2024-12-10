package vk.itmo.teamgray.backend.company.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.company.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
