package com.gumi.enjoytrip.security.service;

import com.gumi.enjoytrip.domain.user.entity.Role;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.exception.UserNotFoundException;
import com.gumi.enjoytrip.domain.user.repository.UserRepository;
import com.gumi.enjoytrip.security.dto.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String SECRET_KEY = "secret-key";
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60; // 1시간
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60; // 30일
    private final UserRepository userRepository;

    public Token generateToken(String email, Role role) {
        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return new Token(accessToken, refreshToken);
    }

    public boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰 만료시간 확인
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean verifyRefreshTokenOwner(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        return user.getRefreshToken().equals(token);
    }

    public User getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        String email = claims.getSubject();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public void saveRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }
}
