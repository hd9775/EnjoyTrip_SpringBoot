package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceDto {
	private long id;
	private String title;
	private String content;
	private int placeType;
	private String createdAt;
	private long creatorId;
	private String creatorNickname;
	private double latitude;
	private double longitude;
	private String date;
}
