package com.gumi.enjoytrip.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    private String email;
    private String password;
    private String nickname;
}
