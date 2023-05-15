package com.gumi.enjoytrip.domain.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RecruitmentListDto {
    private long id;
    private String title;
    private boolean isComplete;
    private int maxCount;
    private int joinCount;
    private long creatorId;
    private String creatorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}
