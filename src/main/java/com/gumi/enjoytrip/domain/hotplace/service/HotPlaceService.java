package com.gumi.enjoytrip.domain.hotplace.service;

import com.gumi.enjoytrip.domain.KakaoRestClient;
import com.gumi.enjoytrip.domain.exception.NoSuchPermissionException;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceCreateDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceListDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceUpdateDto;
import com.gumi.enjoytrip.domain.hotplace.entity.HotPlace;
import com.gumi.enjoytrip.domain.hotplace.exception.HotPlaceNotFoundException;
import com.gumi.enjoytrip.domain.hotplace.repository.HotPlaceRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public List<HotPlaceListDto> getHotPlaceList() {
        return hotPlaceRepository.findAll().stream().map(this::toHotPlaceListDto).toList();
    }

    public HotPlaceDto getHotPlace(long id) {
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        return toHotPlaceDto(hotPlace);
    }

    public Long updateHotPlace(long id, HotPlaceUpdateDto hotPlaceUpdateDto, User user) {
        if(id != hotPlaceUpdateDto.getId()) {
            throw new IllegalArgumentException("핫플레이스 id가 일치하지 않습니다.");
        }
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        if (!Objects.equals(hotPlace.getUser().getId(), user.getId())) {
            throw new NoSuchPermissionException("해당 핫플레이스를 수정할 권한이 없습니다.");
        }
        return hotPlaceRepository.save(hotPlace.update(hotPlaceUpdateDto.toEntity())).getId();
    }

    public void deleteHotPlace(long id, User user) {
        HotPlace hotPlace = hotPlaceRepository.findById(id).orElseThrow(() -> new HotPlaceNotFoundException("해당 핫플레이스가 존재하지 않습니다."));
        if (!Objects.equals(hotPlace.getUser().getId(), user.getId())) {
            throw new NoSuchPermissionException("해당 핫플레이스를 삭제할 권한이 없습니다.");
        }
        hotPlaceRepository.deleteById(id);
    }

    private HotPlaceDto toHotPlaceDto(HotPlace hotPlace) {
        return new HotPlaceDto(
                hotPlace.getId(),
                hotPlace.getName(),
                hotPlace.getAddress(),
                hotPlace.getPlaceType(),
                hotPlace.getCreatedAt(),
                hotPlace.getUser().getId(),
                hotPlace.getUser().getNickname(),
                hotPlace.getLatitude(),
                hotPlace.getLongitude(),
                hotPlace.getVisitDate()
        );
    }

    private HotPlaceListDto toHotPlaceListDto(HotPlace hotPlace) {
        return new HotPlaceListDto(
                hotPlace.getId(),
                hotPlace.getName(),
                hotPlace.getPlaceType(),
                hotPlace.getCreatedAt(),
                hotPlace.getUser().getId(),
                hotPlace.getUser().getNickname()
        );
    }
}
