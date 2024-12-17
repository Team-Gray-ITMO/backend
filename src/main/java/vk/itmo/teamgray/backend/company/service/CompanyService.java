package vk.itmo.teamgray.backend.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.company.dto.CompanyCreateDto;
import vk.itmo.teamgray.backend.company.dto.CompanyDto;
import vk.itmo.teamgray.backend.company.dto.CompanyUpdateDto;
import vk.itmo.teamgray.backend.company.entities.Company;
import vk.itmo.teamgray.backend.company.mapper.CompanyMapper;
import vk.itmo.teamgray.backend.company.repos.CompanyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService extends BaseService<Company> {
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Override
    public Company findEntityById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> DataNotFoundException.entity(Company.class, id));
    }

    public CompanyDto findById(Long id) {
        return companyMapper.toDto(findEntityById(id));
    }

    public CompanyDto createCompany(CompanyCreateDto data) {
        var company = new Company(data);

        company = companyRepository.save(company);

        return companyMapper.toDto(company);
    }

    public CompanyDto updateCompany(CompanyUpdateDto updateDto) {
        var company = findEntityById(updateDto.getId());

        boolean updated = updateIfPresent(updateDto.getName(), company::setName);

        if (updated) {
            company = companyRepository.save(company);
        }

        return companyMapper.toDto(company);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
