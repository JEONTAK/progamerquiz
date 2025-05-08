package pq.progamerquiz.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pq.progamerquiz.common.dto.AuthUser;
import pq.progamerquiz.domain.user.entity.User;

import java.io.IOException;

@Component
public class AuthCookieFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if ("/api/v1/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            if (SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                AuthUser authUser = AuthUser.create(user.getId(), user.getUsername(), user.getUserRole());
                String authUserJson = objectMapper.writeValueAsString(authUser);
                Cookie authCookie = new Cookie("AUTH_USER", authUserJson);
                authCookie.setHttpOnly(true);
                authCookie.setPath("/");
                authCookie.setMaxAge(30 * 60);
                response.addCookie(authCookie);
                String sessionId = request.getSession().getId();
                response.setContentType("application/json");
                response.getWriter().write("{\"sessionId\": \"" + sessionId + "\", \"user\": " + authUserJson + "}");
                response.setStatus(200);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
