package com.gumi.enjoytrip.domain.tourinfo.service;

import com.gumi.enjoytrip.domain.tourinfo.dto.AttractionListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.GugunListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.SidoListDto;
import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import com.gumi.enjoytrip.domain.tourinfo.entity.Gugun;
import com.gumi.enjoytrip.domain.tourinfo.entity.Sido;
import com.gumi.enjoytrip.domain.tourinfo.repository.AttractionInfoRepository;
import com.gumi.enjoytrip.domain.tourinfo.repository.GugunRepository;
import com.gumi.enjoytrip.domain.tourinfo.repository.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    private final SidoRepository sidoRepository;
    private final GugunRepository gugunRepository;
    private final AttractionInfoRepository attractionInfoRepository;

    public List<SidoListDto> getSidoList() {
        List<Sido> sidoList = sidoRepository.findAll();
        return sidoList.stream().map(this::toSidoListDto).toList();
    }

    public List<GugunListDto> getGugunList(int sidoCode) {
        List<Gugun> gugunList = gugunRepository.findBySidoCode(sidoCode);
        return gugunList.stream().map(this::toGugunListDto).toList();
    }

    public List<AttractionListDto> getAttractionList(int sidoCode, int gugunCode, int contentTypeId) {
        List<AttractionInfo> attractionInfoList = attractionInfoRepository.findAllBySidoSidoCodeAndGugunGugunCodeAndContentTypeId(sidoCode, gugunCode, contentTypeId);
        return attractionInfoList.stream().map(this::toAttractionListDto).toList();
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
