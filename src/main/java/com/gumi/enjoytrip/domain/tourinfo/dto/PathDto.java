package com.gumi.enjoytrip.domain.tourinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PathDto {
    private int time;
    private int distance;
    private int taxiFare;
    private int tollFare;
    private double longitude;
    private double latitude;
    private List<RoadListDto> roadList;
}
