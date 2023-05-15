package com.gumi.enjoytrip.domain.participant.dto;

import com.gumi.enjoytrip.domain.participant.entity.Participant;
import com.gumi.enjoytrip.domain.recuritment.entity.Recruitment;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantCreateDto {

    private String comment;

    public Participant toEntity(Recruitment recruitment, User user) {
        return Participant.builder()
                .user(user)
                .recruitment(recruitment)
                .comment(comment)
                .isSelected(false)
                .build();
    }

}
