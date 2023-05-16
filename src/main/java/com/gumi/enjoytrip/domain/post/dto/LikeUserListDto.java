package com.gumi.enjoytrip.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LikeUserListDto {
    private long id;
    private long creatorId;
    private String creatorNickname;
    private String creatorImage;
    private LocalDateTime createdAt;
}
