package vk.itmo.teamgray.backend;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vk.itmo.teamgray.backend.education.repos.EducationInstitutionRepository;
import vk.itmo.teamgray.backend.job.repos.CompanyRepository;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@SpringBootTest
public abstract class TestBase {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EducationInstitutionRepository educationInstitutionRepository;

    @Autowired
    protected ResumeTestGenerator resumeGenerator;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        resumeRepository.deleteAll();
        companyRepository.deleteAll();
        educationInstitutionRepository.deleteAll();
    }
}
