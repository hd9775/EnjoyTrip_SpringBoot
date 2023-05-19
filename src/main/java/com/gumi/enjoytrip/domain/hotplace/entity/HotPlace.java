package com.gumi.enjoytrip.domain.hotplace.entity;

import com.gumi.enjoytrip.domain.BaseTimeEntity;
import com.gumi.enjoytrip.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
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
    private String imageFileName;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate visitDate;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer views;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public HotPlace(String name, String content, Integer placeType, String imageFileName, Double latitude, Double longitude, String address, LocalDate visitDate, int views, User user) {
        this.name = name;
        this.content = content;
        this.placeType = placeType;
        this.imageFileName = imageFileName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.visitDate = visitDate;
        this.views = views;
        this.user = user;
    }

    public void increaseViews() {
        this.views++;
    }

    public HotPlace update(HotPlace hotPlace) {
        if(hotPlace.getName() != null)
            this.name = hotPlace.getName();
        if(hotPlace.getContent() != null)
            this.content = hotPlace.getContent();
        if(hotPlace.getPlaceType() != null)
            this.placeType = hotPlace.getPlaceType();
        if(hotPlace.getLatitude() != null)
            this.latitude = hotPlace.getLatitude();
        if(hotPlace.getLongitude() != null)
            this.longitude = hotPlace.getLongitude();
        if(hotPlace.getAddress() != null)
            this.address = hotPlace.getAddress();
        if(hotPlace.getVisitDate() != null)
            this.visitDate = hotPlace.getVisitDate();
        return this;
    }
}
