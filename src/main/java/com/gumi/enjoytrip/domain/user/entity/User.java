package com.gumi.enjoytrip.domain.user.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String imageFileName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted;

    public User update(User user) {
        if(user.nickname != null)
            this.nickname = user.nickname;
        if(user.password != null)
            this.password = user.password;
        if(user.imageFileName != null)
            this.imageFileName = user.imageFileName;
        if(user.refreshToken != null)
            this.refreshToken = user.refreshToken;
        if(user.isDeleted != null)
            this.isDeleted = user.isDeleted;
        return this;
    }

    @Builder
    public User(String email, String password, String nickname, String imageFileName, Role role, String refreshToken, Boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.imageFileName = imageFileName;
        this.role = role;
        this.refreshToken = refreshToken;
        this.isDeleted = isDeleted;
    }
}
