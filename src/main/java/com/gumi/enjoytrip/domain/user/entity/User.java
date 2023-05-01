package com.gumi.enjoytrip.domain.user.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String refreshToken;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted;

    @Builder
    public User(String email, String password, String nickname, Role role, String refreshToken, Boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.refreshToken = refreshToken;
        this.isDeleted = isDeleted;
    }
}
