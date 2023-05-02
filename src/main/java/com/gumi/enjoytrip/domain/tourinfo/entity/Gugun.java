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
public class Gugun {
    @Id
    private Integer gugunCode;

    @Column
    private String gugunName;

    @Column(nullable = false)
    private Integer sidoCode;
}
