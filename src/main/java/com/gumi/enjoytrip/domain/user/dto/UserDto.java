package com.gumi.enjoytrip.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String email;
    private String nickname;
    private String imageFileName;
    private String role;
}
