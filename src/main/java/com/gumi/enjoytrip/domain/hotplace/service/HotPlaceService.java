package com.gumi.enjoytrip.domain.hotplace.service;

import com.gumi.enjoytrip.domain.KakaoRestClient;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceCreateDto;
import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import com.gumi.enjoytrip.domain.hotplace.repository.HotPlaceRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;
    private final KakaoRestClient kakaoRestClient;

    public Long createHotPlace(HotPlaceCreateDto hotPlaceCreateDto, User user) {
        String address = kakaoRestClient.getAddress(hotPlaceCreateDto.getLatitude(), hotPlaceCreateDto.getLongitude());
        HotPlace hotPlace = hotPlaceCreateDto.toEntity(user, address);
        return hotPlaceRepository.save(hotPlace).getId();
    }
}
