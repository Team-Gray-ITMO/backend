package vk.itmo.teamgray.backend.common.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import vk.itmo.teamgray.backend.user.entities.User;
import vk.itmo.teamgray.backend.user.service.UserService;

@RequiredArgsConstructor
public class VkIdAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;

    private final List<AntPathRequestMatcher> skipMatchers;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (skipMatchers.stream().anyMatch(it -> it.matches(request))) {
            filterChain.doFilter(request, response);
            return;
        }

        String vkId = request.getHeader("x-vk-id");

        if (vkId != null) {
            User user = userService.findByVkId(Long.valueOf(vkId));
            if (user != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
