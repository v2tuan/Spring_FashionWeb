package com.fashionweb.filter;

import com.fashionweb.Entity.Account;
import com.fashionweb.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    // nhung yeu cau o day da pulic r ne khong duoc cau hinh phan quyen nua
    private static final List<EndpointMethod> BYPASS_ENDPOINTS = List.of(
            new EndpointMethod("/login", "GET"),
            new EndpointMethod("/register", "GET"),
            new EndpointMethod("/home/shop", "GET"),
            new EndpointMethod("/home/shop/**", "GET"),
            new EndpointMethod("/assets/**", "GET"),
            new EndpointMethod("/cdn.jsdelivr.net/**", "GET"),
            new EndpointMethod("/cdnjs.cloudflare.com/**", "GET"),
            new EndpointMethod("/api/**", "POST"),
            new EndpointMethod("/login/**", "GET"),
            new EndpointMethod("/forgotpassword/**", "POST"),
            new EndpointMethod("/error/**", "GET"),
            new EndpointMethod("/home/files/**", "GET")


    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isBypassEndpoint(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = extractTokenFromSession(request);
            String requestURI = request.getRequestURI();

            // Kiểm tra nếu token null và request không phải là /home
            if (token == null && !"/home".equals(requestURI)) {
                // Redirect đến trang lỗi
                response.sendRedirect(request.getContextPath() + "/error/401");
                return;
            }
            else if (token != null) {
                authenticateToken(request, token);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
        }
    }

    private String extractTokenFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (String) session.getAttribute("authToken") : null;
    }

    private void authenticateToken(HttpServletRequest request, String token) {
        String email = jwtTokenUtil.extractEmail(token);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Account userDetails = (Account) userDetailsService.loadUserByUsername(email);
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    private boolean isBypassEndpoint(HttpServletRequest request) {
        PathMatcher pathMatcher = new AntPathMatcher();
        return BYPASS_ENDPOINTS.stream().anyMatch(endpoint ->
                pathMatcher.match(endpoint.path, request.getServletPath()) &&
                        request.getMethod().equals(endpoint.method)
        );
    }

    private record EndpointMethod(String path, String method) {}
}