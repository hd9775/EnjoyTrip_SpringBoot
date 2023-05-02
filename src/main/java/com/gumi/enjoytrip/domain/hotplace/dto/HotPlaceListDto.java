package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceListDto {
    private long id;
    private String title;
    private int placeType;
    private String createdAt;
    private long creatorId;
    private String creatorNickname;
}
