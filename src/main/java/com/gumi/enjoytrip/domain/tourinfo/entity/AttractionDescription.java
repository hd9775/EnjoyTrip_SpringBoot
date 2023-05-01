package com.gumi.enjoytrip.domain.tourinfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDescription {
    @Id
    @Column(name = "content_id")
    private Integer contentId;

    @Column
    private String homepage;

    @Column
    private String overview;

    @Column
    private String telname;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "content_id")
    private AttractionInfo attractionInfo;
}
