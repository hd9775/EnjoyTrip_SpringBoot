package com.gumi.enjoytrip.domain.recuriment.dto;

import com.gumi.enjoytrip.domain.recuriment.entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RecruitmentUpdateDto {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private int maxCount;

    public Recruitment toEntity() {
        return Recruitment.builder()
                .title(title)
                .content(content)
                .deadline(deadline)
                .maxCount(maxCount)
                .build();
    }
}
