package com.gumi.enjoytrip.domain.participant.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.recruitment.entity.Recruitment;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Participant extends BaseTimeEntity {
    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @JoinColumn(name = "recruitment_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recruitment recruitment;

    @Column
    private String comment;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isSelected;

    @Builder
    public Participant(Recruitment recruitment, User user, String comment, Boolean isSelected) {
        this.recruitment = recruitment;
        this.user = user;
        this.comment = comment;
        this.isSelected = isSelected;
    }

    public Participant update(Participant participant) {
        if(this.isSelected != participant.isSelected)
            this.isSelected = participant.isSelected;

        return this;
    }
}
