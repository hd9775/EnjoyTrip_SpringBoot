package com.gumi.enjoytrip.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentListDto {
    private long id;
    private long userId;
    private String userNickname;
    private String content;
    private LocalDateTime createAt;
}
