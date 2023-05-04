package com.gumi.enjoytrip.security.service;

import com.gumi.enjoytrip.domain.user.entity.Role;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.security.dto.Token;

public interface TokenService {
    Token generateToken(String email, Role role);
    boolean verifyToken(String token);
    boolean verifyRefreshTokenOwner(String token, String email);
    User getUserFromToken(String token);
}
