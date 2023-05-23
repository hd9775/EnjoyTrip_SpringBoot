package com.gumi.enjoytrip.domain.tourinfo.service;

import com.gumi.enjoytrip.domain.KakaoRestClient;
import com.gumi.enjoytrip.domain.tourinfo.dto.*;
import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import com.gumi.enjoytrip.domain.tourinfo.entity.Gugun;
import com.gumi.enjoytrip.domain.tourinfo.entity.Sido;
import com.gumi.enjoytrip.domain.tourinfo.exception.NotFoundAttractionException;
import com.gumi.enjoytrip.domain.tourinfo.repository.AttractionInfoRepository;
import com.gumi.enjoytrip.domain.tourinfo.repository.GugunRepository;
import com.gumi.enjoytrip.domain.tourinfo.repository.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final AttractionInfoRepository attractionInfoRepository;
    private final KakaoRestClient kakaoRestClient;

    @Transactional(readOnly = true)
    public List<SidoListDto> getSidoList() {
        List<Sido> sidoList = sidoRepository.findAll();
        return sidoList.stream().map(this::toSidoListDto).toList();
    }

    @Transactional(readOnly = true)
    public List<GugunListDto> getGugunList(int sidoCode) {
        List<Gugun> gugunList = gugunRepository.findBySidoCode(sidoCode);
        return gugunList.stream().map(this::toGugunListDto).toList();
    }

    @Transactional(readOnly = true)
    public List<AttractionListDto> getAttractionList(int sidoCode, int gugunCode, int contentTypeId) {
        List<AttractionInfo> attractionInfoList = attractionInfoRepository.findAllBySidoSidoCodeAndGugunGugunCodeAndContentTypeId(sidoCode, gugunCode, contentTypeId);
        return attractionInfoList.stream().map(this::toAttractionListDto).toList();
    }

    @Transactional(readOnly = true)
    public PathDto getPath(int contentId, String keyword) {
        AttractionInfo attractionInfo = attractionInfoRepository.findByContentId(contentId).orElseThrow(() -> new NotFoundAttractionException("해당 관광지를 찾을 수 없습니다."));
        CoordinateDto startCoordinate = kakaoRestClient.getCoordinate(keyword);
        CoordinateDto endCoordinate = new CoordinateDto(attractionInfo.getLatitude(), attractionInfo.getLongitude());
        return kakaoRestClient.getPath(startCoordinate, endCoordinate);
    }


    public SidoListDto toSidoListDto(Sido sido) {
        return new SidoListDto(sido.getSidoCode(), sido.getSidoName());
    }

    public GugunListDto toGugunListDto(Gugun gugun) {
        return new GugunListDto(gugun.getGugunCode(), gugun.getGugunName());
    }

    public AttractionListDto toAttractionListDto(AttractionInfo attractionInfo) {
        return new AttractionListDto(
                attractionInfo.getContentId(),
                attractionInfo.getContentTypeId(),
                attractionInfo.getTitle(),
                attractionInfo.getAddress1(),
                attractionInfo.getFirstImage(),
                attractionInfo.getSido().getSidoCode(),
                attractionInfo.getGugun().getGugunCode(),
                attractionInfo.getLatitude(),
                attractionInfo.getLongitude()
        );
    }
}
