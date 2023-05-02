package com.gumi.enjoytrip.domain.hotplace.dto;

import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceUpdateDto {
    private long id;
    private String name;
    private String content;
    private int placeType;
    private Date visitDate;

    public HotPlace toEntity() {
        return HotPlace.builder()
                .name(name)
                .content(content)
                .placeType(placeType)
                .visitDate(visitDate)
                .build();
    }
}
