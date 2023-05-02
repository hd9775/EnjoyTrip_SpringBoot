package com.gumi.enjoytrip.domain.tourinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttractionListDto {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String address;
    private String image;
    private int sidoCode;
    private int gugunCode;
    private double latitude;
    private double longitude;
}
