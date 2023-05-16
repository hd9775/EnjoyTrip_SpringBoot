package com.gumi.enjoytrip.domain.participant.controller;

import com.gumi.enjoytrip.domain.participant.dto.ParticipantCreateDto;
import com.gumi.enjoytrip.domain.participant.dto.ParticipantListDto;
import com.gumi.enjoytrip.domain.participant.service.ParticipantService;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;
    private final UserService userService;

    @Operation(summary = "참여자 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여자 목록 조회 성공"),
            @ApiResponse(responseCode = "403", description = "작성자만 조회할 수 있습니다.")
    })
    @GetMapping("/{id}")
    public List<ParticipantListDto> getParticipants(@PathVariable long id) {
        return participantService.getParticipantList(id, userService.getLoginUser());
    }

    @Operation(summary = "참여")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여 성공"),
            @ApiResponse(responseCode = "404", description = "모집글이 존재하지 않습니다."),
    })
    @PostMapping("/{id}")
    public void createParticipant(@PathVariable long id, @RequestBody ParticipantCreateDto participantCreateDto) {
        participantService.createParticipant(id, participantCreateDto, userService.getLoginUser());
    }

    @Operation(summary = "참여 해제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여 해제 성공"),
            @ApiResponse(responseCode = "403", description = "참여자만 삭제할 수 있습니다."),
            @ApiResponse(responseCode = "404", description = "모집글 혹은 참여정보가 존재하지 않습니다."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable long id) {
        participantService.deleteParticipant(id, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "참여자 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설정 성공"),
            @ApiResponse(responseCode = "403", description = "작성자만 수정할 수 있습니다."),
            @ApiResponse(responseCode = "404", description = "모집글 혹은 참여정보가 존재하지 않습니다."),
    })
    @PatchMapping(value = "/{id}/select")
    public void toggleParticipant(@PathVariable long id) {
        participantService.toggleParticipantSelect(id, userService.getLoginUser());
    }

}
