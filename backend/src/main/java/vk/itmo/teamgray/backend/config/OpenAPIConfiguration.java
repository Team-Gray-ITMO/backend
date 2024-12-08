package vk.itmo.teamgray.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static vk.itmo.teamgray.backend.config.ApplicationConfiguration.API_VER;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
            .group("User")
            .pathsToMatch(API_VER + "/user/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicTemplateApi() {
        return GroupedOpenApi.builder()
            .group("Template")
            .pathsToMatch(API_VER + "/template/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicSkillApi() {
        return GroupedOpenApi.builder()
            .group("Skill")
            .pathsToMatch(API_VER + "/skill/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicResumeApi() {
        return GroupedOpenApi.builder()
            .group("Resume")
            .pathsToMatch(API_VER + "/resume/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicLanguageApi() {
        return GroupedOpenApi.builder()
            .group("Language")
            .pathsToMatch(API_VER + "/language/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicJobApi() {
        return GroupedOpenApi.builder()
            .group("Job")
            .pathsToMatch(API_VER + "/job/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicCompanyApi() {
        return GroupedOpenApi.builder()
            .group("Company")
            .pathsToMatch(API_VER + "/company/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicEducationApi() {
        return GroupedOpenApi.builder()
            .group("Education")
            .pathsToMatch(API_VER + "/education/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicEducationInstitutionApi() {
        return GroupedOpenApi.builder()
            .group("Education Institution")
            .pathsToMatch(API_VER + "/education-institution/**")
            .build();
    }

    @Bean
    public GroupedOpenApi publicCertificationApi() {
        return GroupedOpenApi.builder()
            .group("Certification")
            .pathsToMatch(API_VER + "/certification/**")
            .build();
    }

    @Bean
    public OpenAPI customOpenApi(
        @Value("${server.port}") String port
    ) {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Application API")
            )
            .servers(
                List.of(
                    new Server().url("http://localhost:" + port)
                        .description("Local Instance")
                )
            );
    }
}
