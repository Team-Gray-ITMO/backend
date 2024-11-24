package vk.itmo.teamgray.backend.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.job.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.job.dto.CompanyUpdateDto;
import vk.itmo.teamgray.backend.job.entities.Company;
import vk.itmo.teamgray.backend.job.repos.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Company createCompany(CompanyCreateDto data) {
        return companyRepository.save(new Company(
            data
        ));
    }

    public Company updateCompany(CompanyUpdateDto data) {
        return companyRepository.save(new Company(
            data
        ));
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
