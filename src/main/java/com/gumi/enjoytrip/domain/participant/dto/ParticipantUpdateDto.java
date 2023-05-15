package com.gumi.enjoytrip.domain.participant.dto;

import com.gumi.enjoytrip.domain.participant.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantUpdateDto {

    private Long id;
    private String comment;
    private Boolean isSelected;

    public Participant toEntity() {
        return Participant.builder()
                .comment(comment)
                .isSelected(isSelected)
                .build();
    }

}
