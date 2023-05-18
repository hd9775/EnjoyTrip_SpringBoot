package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceListDto {
    private long id;
    private String name;
    private int placeType;
    private String imageFileName;
    private LocalDateTime createdAt;
    private long creatorId;
    private String creatorNickname;
}
