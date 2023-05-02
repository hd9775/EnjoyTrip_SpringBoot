package com.gumi.enjoytrip.domain.hotplace.dto;

import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceCreateDto {
    private String name;
    private String content;
    private int placeType;
    private double latitude;
    private double longitude;
    private Date visitDate;

    public HotPlace toEntity(User user, String address) {
        return HotPlace.builder()
                .name(name)
                .content(content)
                .placeType(placeType)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .user(user)
                .visitDate(visitDate)
                .build();
    }
}
