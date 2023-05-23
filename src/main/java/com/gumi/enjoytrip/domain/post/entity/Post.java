package com.gumi.enjoytrip.domain.post.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isNotice;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public Post(String title, String content, int views, boolean isNotice, User user) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.isNotice = isNotice;
        this.user = user;
    }

    public Post update(Post post) {
        if (post.title != null)
            this.title = post.title;
        if (post.content != null)
            this.content = post.content;
        if (post.isNotice != null)
            this.isNotice = post.isNotice;
        return this;
    }

    public void increaseViews() {
        this.views++;
    }
}
