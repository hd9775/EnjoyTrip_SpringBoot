package com.gumi.enjoytrip.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProfileDto {
    private long id;
    private String email;
    private String nickname;
    private String imageFileName;
    private String role;
    private LocalDateTime createdAt;
    private int postCount;
    private int commentCount;
    private int hotPlaceCount;
}
