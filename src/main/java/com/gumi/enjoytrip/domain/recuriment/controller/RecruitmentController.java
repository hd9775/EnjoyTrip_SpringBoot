package com.gumi.enjoytrip.domain.recuriment.controller;

import com.gumi.enjoytrip.domain.recuriment.dto.RecruitmentCreateDto;
import com.gumi.enjoytrip.domain.recuriment.dto.RecruitmentDto;
import com.gumi.enjoytrip.domain.recuriment.dto.RecruitmentListDto;
import com.gumi.enjoytrip.domain.recuriment.dto.RecruitmentUpdateDto;
import com.gumi.enjoytrip.domain.recuriment.service.RecruitmentService;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final UserService userService;

    @Operation(summary = "모집글 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모집글 목록 조회 성공")
    })
    @GetMapping("/")
    public List<RecruitmentListDto> getRecruitments() {
        return recruitmentService.getRecruitmentList();
    }

    @Operation(summary = "모집글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모집글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집글입니다.")
    })
    @PostMapping("/{id}")
    public ResponseEntity<RecruitmentDto> getRecruitment(@PathVariable long id) {
        RecruitmentDto RecruitmentDto = recruitmentService.getRecruitment(id, userService.getLoginUser());
        return ResponseEntity.ok(RecruitmentDto);
    }

    @Operation(summary = "모집글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모집글 작성 성공")
    })
    @PostMapping("/")
    public long createRecruitment(@RequestBody RecruitmentCreateDto RecruitmentCreateDto) {
        return recruitmentService.createRecruitment(RecruitmentCreateDto, userService.getLoginUser());
    }

    @Operation(summary = "모집글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모집글 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집글입니다."),
            @ApiResponse(responseCode = "403", description = "작성자가 아닙니다.")
    })
    @PutMapping("/{id}")
    public Long updateRecruitment(@PathVariable long id, @RequestBody RecruitmentUpdateDto RecruitmentDto) {
        return recruitmentService.updateRecruitment(id, RecruitmentDto, userService.getLoginUser());
    }

    @Operation(summary = "모집글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모집글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 모집글입니다."),
            @ApiResponse(responseCode = "403", description = "작성자가 아닙니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruitment(long id) {
        recruitmentService.deleteRecruitment(id, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

}
