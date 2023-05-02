package com.gumi.enjoytrip.domain.hotplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HotPlaceDto {
	private long id;
	private String title;
	private String address;
	private int placeType;
	private LocalDateTime createdAt;
	private long creatorId;
	private String creatorNickname;
	private double latitude;
	private double longitude;
	private Date visitDate;
}
