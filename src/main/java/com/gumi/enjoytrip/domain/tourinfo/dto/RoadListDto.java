package com.gumi.enjoytrip.domain.tourinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoadListDto {
    private int trafficState;
    private List<CoordinateDto> coordinates;
}
