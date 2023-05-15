package com.gumi.enjoytrip.domain.participant.dto;

import com.gumi.enjoytrip.domain.recuritment.entity.Recruitment;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantDto {
    private Long id;
    private User user;
    private Recruitment recruitment;
    private String comment;
    private Boolean isSelected;
}
