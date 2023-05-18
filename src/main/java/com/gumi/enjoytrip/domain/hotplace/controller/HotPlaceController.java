package com.gumi.enjoytrip.domain.hotplace.controller;

import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceCreateDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceListDto;
import com.gumi.enjoytrip.domain.hotplace.dto.HotPlaceUpdateDto;
import com.gumi.enjoytrip.domain.hotplace.service.HotPlaceService;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/hot-places")
@RequiredArgsConstructor
public class HotPlaceController {

    private final UserService userService;
    private final HotPlaceService hotPlaceService;

    @Operation(summary = "핫플레이스 생성")
    @ApiResponse(responseCode = "201", description = "핫플레이스 생성 성공")
    @PostMapping("")
    public ResponseEntity<Long> createHotPlace(@RequestParam("file") MultipartFile file, @ModelAttribute HotPlaceCreateDto hotPlaceCreateDto) {
        User user = userService.getLoginUser();
        return ResponseEntity.created(null).body(hotPlaceService.createHotPlace(hotPlaceCreateDto, file, user));
    }

    @Operation(summary = "핫플레이스 목록 조회")
    @ApiResponse(responseCode = "200", description = "핫플레이스 목록 조회 성공")
    @GetMapping("")
    public List<HotPlaceListDto> getHotPlaces() {
        return hotPlaceService.getHotPlaceList();
    }

    @Operation(summary = "핫플레이스 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "핫플레이스 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "핫플레이스를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public HotPlaceDto getHotPlace(@PathVariable long id) {
        return hotPlaceService.getHotPlace(id);
    }

    @Operation(summary = "핫플레이스 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "핫플레이스 수정 성공"),
            @ApiResponse(responseCode = "404", description = "핫플레이스를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.")
    })
    @PutMapping("/{id}")
    public Long updateHotPlace(@PathVariable long id, @RequestBody HotPlaceUpdateDto hotPlaceUpdateDto) {
        User user = userService.getLoginUser();
        return hotPlaceService.updateHotPlace(id, hotPlaceUpdateDto, user);
    }

    @Operation(summary = "핫플레이스 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "핫플레이스 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "핫플레이스를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotPlace(@PathVariable long id) {
        User user = userService.getLoginUser();
        hotPlaceService.deleteHotPlace(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) {
        try {
            Path imageFilePath = Paths.get("static/hot_place_images").resolve(filename).normalize();
            Resource resource = new ClassPathResource(imageFilePath.toString());

            String mediaType = Files.probeContentType(imageFilePath);
            if (mediaType == null) {
                mediaType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaType))
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
