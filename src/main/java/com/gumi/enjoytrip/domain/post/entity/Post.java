package com.gumi.enjoytrip.domain.post.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.post.dto.PostDto;
import com.gumi.enjoytrip.domain.post.dto.PostUpdateDto;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isNotice;

    @JoinColumn(nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Post update(Post post) {
        if(post.title != null) {
            this.title = post.title;
        }
        if(post.content != null) {
            this.content = post.content;
        }
        if(post.views != this.views) {
            this.views = post.views;
        }
        if(post.isNotice != this.isNotice) {
            this.isNotice = post.isNotice;
        }

        return this;
    }

    @Builder
    public Post(String title, String content, int views, boolean isNotice, User user) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.isNotice = isNotice;
        this.user = user;
    }

}
