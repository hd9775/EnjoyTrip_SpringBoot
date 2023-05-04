package com.gumi.enjoytrip.domain.user.dto;

import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
public class UserPasswordUpdateDto {
    private long id;
    private String oldPassword;
    private String newPassword;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .password(passwordEncoder.encode(newPassword))
                .build();
    }
}
