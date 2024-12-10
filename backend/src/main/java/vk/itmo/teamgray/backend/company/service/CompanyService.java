package vk.itmo.teamgray.backend.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.company.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.company.dto.CompanyDto;
import vk.itmo.teamgray.backend.company.dto.CompanyUpdateDto;
import vk.itmo.teamgray.backend.company.entities.Company;
import vk.itmo.teamgray.backend.company.mapper.CompanyMapper;
import vk.itmo.teamgray.backend.company.repos.CompanyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public Company findEntityById(Long id) {
        return companyRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public CompanyDto findById(Long id) {
        return companyMapper.toDto(findEntityById(id));
    }

    public CompanyDto createCompany(CompanyCreateDto data) {
        return createCompany(data, true);
    }

    public CompanyDto createCompany(CompanyCreateDto data, boolean persist) {
        var company = new Company(data);

        if (persist) {
            company = companyRepository.save(company);
        }

        return companyMapper.toDto(company);
    }

    public CompanyDto updateCompany(CompanyUpdateDto data) {
        return companyMapper.toDto(
            companyRepository.save(new Company(
                data
            ))
        );
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}