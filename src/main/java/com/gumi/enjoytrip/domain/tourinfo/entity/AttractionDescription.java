package com.gumi.enjoytrip.domain.tourinfo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDescription {
    @Id
    private Integer contentId;

    @Column
    private String homepage;

    @Column
    private String overview;

    @Column
    private String telname;
}
