package com.gumi.enjoytrip.security.filter;

import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.RequestMatcherProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        if (StringUtils.hasText(token)) {
            // 토큰이 있는 경우
            if (tokenService.verifyToken(token)) {
                User user = tokenService.getUserFromToken(token);
                Authentication authentication = getAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            // 토큰이 없는 경우
            // permitAll()로 허용한 URL 패턴에 대해서는 인증 처리를 수행하지 않고 통과시킴
            if (isPermitAllRequest(request)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Authentication getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }

    private boolean isPermitAllRequest(HttpServletRequest request) {
        List<RequestMatcher> matchers = new ArrayList<>();

        // HttpSecurity 클래스에서 permitAll()로 허용한 URL 패턴 가져오기
        matchers.add(new AntPathRequestMatcher("/api/v1/users/login"));
        matchers.add(new AntPathRequestMatcher("/api/v1/users/signup"));
        matchers.add(new AntPathRequestMatcher("/api/v1/tours/**"));
        matchers.add(new AntPathRequestMatcher("/"));

        matchers.add(new AntPathRequestMatcher("/v3/api-docs/**"));
        matchers.add(new AntPathRequestMatcher("/swagger-ui.html"));
        matchers.add(new AntPathRequestMatcher("/swagger-ui/**"));

        // 요청 URL이 permitAll()로 허용한 URL 패턴에 해당하는지 확인
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
