package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceDto {
	private long id;
	private String name;
	private String content;
	private String address;
	private int placeType;
	private String imageFileName;
	private LocalDateTime createdAt;
	private long creatorId;
	private String creatorImage;
	private String creatorNickname;
	private double latitude;
	private double longitude;
	private LocalDate visitDate;
	private int views;
}
