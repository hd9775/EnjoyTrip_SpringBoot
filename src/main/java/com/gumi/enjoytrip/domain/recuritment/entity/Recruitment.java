package com.gumi.enjoytrip.domain.recuritment.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recruitment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer maxCount;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isComplete;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Recruitment update(Recruitment recruitment) {
        if(recruitment.title != null)
            this.title = recruitment.title;
        if(recruitment.content != null)
            this.content = recruitment.content;
        if(recruitment.maxCount != null)
            this.maxCount = recruitment.maxCount;
        if(recruitment.isComplete != this.isComplete)
            this.isComplete = recruitment.isComplete;
        return this;
    }

    @Builder
    public Recruitment(String title, String content, int maxCount, LocalDateTime deadline, boolean isComplete, User user) {
        this.title = title;
        this.content = content;
        this.maxCount = maxCount;
        this.deadline = deadline;
        this.isComplete = isComplete;
        this.user = user;
    }
}
