package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceCreateDto {
    private String title;
    private String content;
    private int placeType;
    private double latitude;
    private double longitude;
    private long creatorId;
    private Date date;
}
