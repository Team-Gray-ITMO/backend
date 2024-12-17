package vk.itmo.teamgray.backend.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.company.service.CompanyService;
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
public class JobService extends BaseService {
    private final JobRepository jobRepository;
    private final ResumeService resumeService;
    private final CompanyService companyService;
    private final JobMapper jobMapper;

    @Override
    public Job findEntityById(Long id) {
        return jobRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Job.class, id));
    }

    public JobDto findById(Long id) {
        return jobMapper.toDto(findEntityById(id));
    }

    public JobDto createJob(JobCreateDto data) {
        var resume = resumeService.findEntityById(data.getResumeId());
        var company = companyService.findEntityById(data.getCompanyId());

        var job = new Job(data, resume, company);

        job = jobRepository.save(job);

        return jobMapper.toDto(job);
    }

    public JobDto updateJob(JobUpdateDto updateDto) {
        var job = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getTitle(), job::setTitle);
        updated |= updateIfPresent(updateDto.getLocation(), job::setLocation);
        updated |= updateIfPresent(updateDto.getStartDate(), job::setStartDate);
        updated |= updateIfPresent(updateDto.getEndDate(), job::setEndDate);
        updated |= updateIfPresent(updateDto.getDescription(), job::setDescription);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), job::setResume);
        updated |= companyService.updateLinkToEntityIfPresent(updateDto.getCompanyId(), job::setCompany);

        if (updated) {
            job = jobRepository.save(job);
        }

        return jobMapper.toDto(job);
    }

    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}
