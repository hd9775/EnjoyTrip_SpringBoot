package com.gumi.enjoytrip.domain.hotplace.dto;

import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceCreateDto {
    private String name;
    private String content;
    private int placeType;
    private double latitude;
    private double longitude;
    private LocalDate visitDate;

    public HotPlace toEntity(User user, String address, String imageFileName) {
        return HotPlace.builder()
                .name(name)
                .content(content)
                .placeType(placeType)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .imageFileName(imageFileName)
                .user(user)
                .visitDate(visitDate)
                .build();
    }
}
