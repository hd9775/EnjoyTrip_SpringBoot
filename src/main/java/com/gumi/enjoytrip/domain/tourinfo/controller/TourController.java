package com.gumi.enjoytrip.domain.tourinfo.controller;

import com.gumi.enjoytrip.domain.tourinfo.dto.AttractionListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.GugunListDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.PathDto;
import com.gumi.enjoytrip.domain.tourinfo.dto.SidoListDto;
import com.gumi.enjoytrip.domain.tourinfo.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tours")
@RequiredArgsConstructor
public class TourController {
    private final TourService tourService;

    @Operation(summary = "시도 목록 조회")
    @ApiResponse(responseCode = "200", description = "시도 목록 조회 성공")
    @GetMapping("/sidos")
    public List<SidoListDto> getSidos() {
        return tourService.getSidoList();
    }

    @Operation(summary = "구군 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "구군 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "시도 코드가 잘못되었습니다.")
    })
    @GetMapping("/guguns")
    public List<GugunListDto> getGuguns(@RequestParam int sidoCode) {
        return tourService.getGugunList(sidoCode);
    }

    @Operation(summary = "관광지 목록 조회")
    @ApiResponse(responseCode = "200", description = "관광지 목록 조회 성공")
    @GetMapping("/attractions")
    public List<AttractionListDto> getAttractions(@RequestParam int sidoCode, @RequestParam int gugunCode, @RequestParam int contentTypeId) {
        return tourService.getAttractionList(sidoCode, gugunCode, contentTypeId);
    }

    @Operation(summary = "경로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "경로 조회 성공"),
            @ApiResponse(responseCode = "404", description = "관광지가 존재하지 않습니다.")
    })
    @GetMapping("/paths")
    public PathDto getPath(@RequestParam int contentId, @RequestParam String keyword)
    {
        return tourService.getPath(contentId, keyword);
    }
}
