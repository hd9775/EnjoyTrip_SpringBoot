package com.gumi.enjoytrip.domain.hotplace.controller;

import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceCreateDto;
import com.gumi.enjoytrip.domain.hotplace.service.HotPlaceService;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hot-places")
@RequiredArgsConstructor
public class HotPlaceController {

    private final UserService userService;
    private final HotPlaceService hotPlaceService;

    @Operation(summary = "핫플레이스 생성")
    @ApiResponse(responseCode = "200", description = "핫플레이스 생성 성공")
    @PostMapping("/")
    public ResponseEntity<Long> createHotPlace(@RequestBody HotPlaceCreateDto hotPlaceCreateDto) {
        User user = userService.getLoginUser();
        return ResponseEntity.ok(hotPlaceService.createHotPlace(hotPlaceCreateDto, user));
    }
}
