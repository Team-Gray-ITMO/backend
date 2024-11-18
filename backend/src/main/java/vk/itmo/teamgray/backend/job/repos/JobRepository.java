package vk.itmo.teamgray.backend.job.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.job.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}