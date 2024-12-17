package vk.itmo.teamgray.backend.common.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Profile("!test")
@Component
public class ClientIdFilter extends OncePerRequestFilter {
    @Value("${vk.client.id:null}")
    private String clientId;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String clientId = request.getHeader("X-Client-Id");
        if (clientId == null || clientId.isEmpty() || !clientId.equals(this.clientId)) {
            throw new ServletException("X-Client-Id mismatch");
        }

        filterChain.doFilter(request, response);
    }
}
