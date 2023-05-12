package com.gumi.enjoytrip.domain.recuriment.dto;

import com.gumi.enjoytrip.domain.recuriment.entity.Recruitment;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RecruitmentCreateDto {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private int maxCount;

    public Recruitment toEntity(User user) {
        return Recruitment.builder()
                .title(title)
                .content(content)
                .deadline(deadline)
                .maxCount(maxCount)
                .user(user)
                .build();
    }
}
