package com.gumi.enjoytrip.domain.participant.dto;

import com.gumi.enjoytrip.domain.participant.entity.Participant;
import com.gumi.enjoytrip.domain.recuritment.entity.Recruitment;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParticipantCreateDto {

    private Recruitment recruitment;
    private String comment;

    public Participant toEntity(User user) {
        return Participant.builder()
                .user(user)
                .recruitment(recruitment)
                .comment(comment)
                .build();
    }

}
