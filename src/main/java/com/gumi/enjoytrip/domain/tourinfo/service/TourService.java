package com.gumi.enjoytrip.domain.tourinfo.service;

import com.gumi.enjoytrip.domain.tourinfo.dto.AttractionListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.GugunListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.SidoListDto;
import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionDescription;
import com.gumi.enjoytrip.domain.tourinfo.entity.AttractionInfo;
import com.gumi.enjoytrip.domain.tourinfo.entity.Gugun;
import com.gumi.enjoytrip.domain.tourinfo.entity.Sido;
import com.gumi.enjoytrip.domain.tourinfo.exception.InvalidSidoCodeException;
import com.gumi.enjoytrip.domain.tourinfo.repository.AttractionDescriptionRepository;
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
    private final AttractionDescriptionRepository attractionDescriptionRepository;

    public List<SidoListDto> getSidoList() {
        List<Sido> sidoList = sidoRepository.findAll();
        return sidoList.stream().map(this::toSidoListDto).toList();
    }

    public List<GugunListDto> getGugunList(int sidoCode) {
        List<Gugun> gugunList = gugunRepository.findBySidoCode(sidoCode);
        if(gugunList.isEmpty())
            throw new InvalidSidoCodeException("시도 코드가 잘못되었습니다.");
        return gugunList.stream().map(this::toGugunListDto).toList();
    }

    public List<AttractionListDto> getAttractionList(int sidoCode, int gugunCode, int contentTypeId) {
        List<AttractionDescription> attractionInfoList = attractionDescriptionRepository.findByAttractionInfo_Sido_SidoCodeAndAttractionInfo_Gugun_GugunCodeAndAttractionInfo_ContentTypeId(sidoCode, gugunCode, contentTypeId);
        return attractionInfoList.stream().map(this::toAttractionListDto).toList();
    }

    public SidoListDto toSidoListDto(Sido sido) {
        return new SidoListDto(sido.getSidoCode(), sido.getSidoName());
    }

    public GugunListDto toGugunListDto(Gugun gugun) {
        return new GugunListDto(gugun.getGugunCode(), gugun.getGugunName());
    }

    public AttractionListDto toAttractionListDto(AttractionDescription attractionDescription) {
        return new AttractionListDto(
                attractionDescription.getAttractionInfo().getContentId(),
                attractionDescription.getAttractionInfo().getContentTypeId(),
                attractionDescription.getAttractionInfo().getTitle(),
                attractionDescription.getAttractionInfo().getAddress1(),
                attractionDescription.getAttractionInfo().getFirstImage(),
                attractionDescription.getOverview(),
                attractionDescription.getAttractionInfo().getSido().getSidoCode(),
                attractionDescription.getAttractionInfo().getGugun().getGugunCode(),
                attractionDescription.getAttractionInfo().getLatitude(),
                attractionDescription.getAttractionInfo().getLongitude()
                );
    }
}
