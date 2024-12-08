package vk.itmo.teamgray.backend.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.job.dto.JobCreateDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.job.dto.JobUpdateDto;
import vk.itmo.teamgray.backend.job.entities.Job;
import vk.itmo.teamgray.backend.job.mapper.JobMapper;
import vk.itmo.teamgray.backend.job.repos.JobRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class JobService {
    private final JobRepository jobRepository;
    private final ResumeService resumeService;
    private final CompanyService companyService;
    private final JobMapper jobMapper;

    public Job findEntityById(Long id) {
        return jobRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public JobDto findById(Long id) {
        return jobMapper.toDto(findEntityById(id));
    }

    public JobDto createJob(JobCreateDto data) {
        return jobMapper.toDto(
            jobRepository.save(new Job(
                data,
                resumeService.findEntityById(data.resumeId()),
                companyService.findEntityById(data.companyId())
            ))
        );
    }

    public JobDto updateJob(JobUpdateDto data) {
        return jobMapper.toDto(
            jobRepository.save(new Job(
                data,
                resumeService.findEntityById(data.resumeId()),
                companyService.findEntityById(data.companyId())
            ))
        );
    }

    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}
