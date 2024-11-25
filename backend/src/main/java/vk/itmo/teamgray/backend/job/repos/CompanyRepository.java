package vk.itmo.teamgray.backend.job.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.job.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}