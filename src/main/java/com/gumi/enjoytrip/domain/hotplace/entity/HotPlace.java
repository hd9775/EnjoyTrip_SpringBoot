package com.gumi.enjoytrip.domain.hotplace.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class HotPlace extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotplace_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer placeType;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date visitDate;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public HotPlace(String name, String content, Integer placeType, Double latitude, Double longitude, String address, Date visitDate, User user) {
        this.name = name;
        this.content = content;
        this.placeType = placeType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.visitDate = visitDate;
        this.user = user;
    }
}
