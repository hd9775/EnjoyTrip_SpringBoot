package com.gumi.enjoytrip.domain.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantListDto {
    private Long id;
    private Long userId;
    private String userNickName;
    private String comment;
    private Boolean isSelected;
}
