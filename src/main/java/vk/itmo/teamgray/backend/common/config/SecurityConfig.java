package vk.itmo.teamgray.backend.common.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vk.itmo.teamgray.backend.common.filters.VkIdAuthenticationFilter;
import vk.itmo.teamgray.backend.user.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;
import static vk.itmo.teamgray.backend.common.config.ApplicationConfiguration.API_VER;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final RequestMatcher[] allowMatchers = {
        new AntPathRequestMatcher(API_VER + "/user", "POST"),
        new AntPathRequestMatcher(API_VER + "/user/vk/**"),
        new AntPathRequestMatcher("/swagger-ui/**"),
        new AntPathRequestMatcher("/v3/api-docs/**"),
        new AntPathRequestMatcher("/v3/api-docs.yaml")
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public VkIdAuthenticationFilter vkIdAuthenticationFilter(UserService userService) {
        return new VkIdAuthenticationFilter(
            userService,
            //Letting these endpoints skip VK ID filter, but not others.
            Arrays.asList(allowMatchers)
        );
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, VkIdAuthenticationFilter vkIdFilter) throws Exception {
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
        );

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
            .formLogin(withDefaults())
            .addFilterBefore(vkIdFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    // TODO Переделать на anonymous
                    .requestMatchers(allowMatchers).permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(c -> c.authenticationEntryPoint(
                (request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value())
            ));


        return http.build();
    }
}
