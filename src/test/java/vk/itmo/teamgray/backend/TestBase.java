package vk.itmo.teamgray.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vk.itmo.teamgray.backend.company.repos.CompanyRepository;
import vk.itmo.teamgray.backend.educationinstitution.repos.EducationInstitutionRepository;
import vk.itmo.teamgray.backend.resume.generator.ResumeTestGenerator;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.template.dto.TemplateDto;
import vk.itmo.teamgray.backend.template.repos.TemplateRepository;
import vk.itmo.teamgray.backend.template.services.TemplateImportService;
import vk.itmo.teamgray.backend.template.services.TemplateService;
import vk.itmo.teamgray.backend.user.dto.PreferredContact;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.dto.UserDto;
import vk.itmo.teamgray.backend.user.repos.UserRepository;
import vk.itmo.teamgray.backend.user.service.UserService;

@SpringBootTest
public abstract class TestBase {
    @Autowired
    protected ResumeRepository resumeRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected TemplateRepository templateRepository;

    @Autowired
    protected EducationInstitutionRepository educationInstitutionRepository;

    @Autowired
    protected TemplateService templateService;

    @Autowired
    protected ResumeTestGenerator resumeGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected TemplateImportService templateImportService;

    protected UserDto testUser;

    protected TemplateDto sampleTemplate;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        resumeRepository.deleteAll();
        companyRepository.deleteAll();
        educationInstitutionRepository.deleteAll();
        templateRepository.deleteAll();

        templateImportService.init();

        sampleTemplate = templateService.findAll().stream()
            .max(Comparator.comparing(TemplateDto::getCreatedAt))
            .orElseThrow();

        testUser = userService.createUser(
            new UserCreateDto(
                "email@example.com",
                69696969L,
                "+79999696969",
                Date.from(Instant.now()),
                "г. Санкт-Петербург",
                PreferredContact.EMAIL
            )
        );

        var userEntity = userService.findEntityById(testUser.getId());

        //TODO Implement better test auth
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
