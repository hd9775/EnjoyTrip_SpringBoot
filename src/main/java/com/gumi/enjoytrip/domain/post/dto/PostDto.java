package com.gumi.enjoytrip.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String content;
    private int views;
    private boolean isLiked;
    private boolean isNotice;
    private int likeCount;
    private long creatorId;
    private String creatorNickname;
    private LocalDateTime createdAt;
}
