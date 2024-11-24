package vk.itmo.teamgray.backend.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobUpdateDto;
import vk.itmo.teamgray.backend.job.entities.Job;
import vk.itmo.teamgray.backend.job.repos.JobRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class JobService {
    private final JobRepository jobRepository;
    private final ResumeService resumeService;
    private final CompanyService companyService;

    public Job findById(Long id) {
        return jobRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Job createJob(JobCreateDto data) {
        return jobRepository.save(new Job(
            data,
            resumeService.findById(data.resumeId()),
            companyService.findById(data.companyId())
        ));
    }

    public Job updateJob(JobUpdateDto data) {
        return jobRepository.save(new Job(
            data,
            resumeService.findById(data.resumeId()),
            companyService.findById(data.companyId())
        ));
    }

    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}
