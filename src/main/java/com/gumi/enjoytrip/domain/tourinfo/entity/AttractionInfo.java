package com.gumi.enjoytrip.domain.tourinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionInfo {
    @Id
    @Column(name = "content_id")
    private Integer contentId;

    @Column
    private Integer contentTypeId;

    @Column
    private String title;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String zipcode;

    @Column
    private String tel;

    @Column
    private String firstImage;

    @Column
    private String firstImage2;

    @Column
    private Integer readCount;

    @JoinColumn(name = "sido_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sido sido;

    @JoinColumn(name = "gugun_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Gugun gugun;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String mLevel;
}
