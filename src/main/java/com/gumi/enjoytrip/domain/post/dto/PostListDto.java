package com.gumi.enjoytrip.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostListDto {
    private long id;
    private String title;
    private int views;
    private boolean isNotice;
    private int likeCount;
    private int commentCount;
    private long creatorId;
    private String creatorNickname;
    private LocalDateTime createdAt;
}
