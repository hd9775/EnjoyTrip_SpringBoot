package com.gumi.enjoytrip.domain.user.controller;

import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.service.PostService;
import com.gumi.enjoytrip.domain.user.dto.LoginDto;
import com.gumi.enjoytrip.domain.user.dto.ProfileDto;
import com.gumi.enjoytrip.domain.user.dto.UserCreateDto;
import com.gumi.enjoytrip.domain.user.dto.UserDto;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.service.UserService;
import com.gumi.enjoytrip.security.dto.Token;
import com.gumi.enjoytrip.security.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto, passwordEncoder);
        return ResponseEntity.ok(tokenService.generateToken(user.getEmail(), user.getRole()));
    }

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일입니다.")
    })
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody UserCreateDto userCreateDto) {
        userService.join(userCreateDto, passwordEncoder);
        return ResponseEntity.created(null).build();
    }

    @Operation(summary = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "회원정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @PatchMapping("/profile-nickname")
    public ResponseEntity<Void> updateProfileNickname(@RequestParam String nickname) {
        userService.updateProfileNickname(nickname, userService.getLoginUser());
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<Void> updateProfileImage(@RequestParam MultipartFile image) {
        // TODO: 이미지 업로드
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestParam String password, @RequestParam String newPassword) {
        userService.changePassword(password, newPassword, userService.getLoginUser(), passwordEncoder);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "회원탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(userService.getLoginUser());
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "회원정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ProfileDto getUser(@PathVariable Long id) {
        return userService.getProfile(id);
    }

    @Operation(summary = "내 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.")
    })
    @GetMapping("/me")
    public UserDto getMyUser() {
        return userService.getMyUser();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) {
        try {
            Path imageFilePath = Paths.get("static/profile_images").resolve(filename).normalize();
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
