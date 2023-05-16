package com.gumi.enjoytrip.domain.post.controller;

import com.gumi.enjoytrip.domain.post.dto.*;
import com.gumi.enjoytrip.domain.post.service.PostService;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Operation(summary = "게시글 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    @GetMapping("")
    public List<PostListDto> getPosts(@RequestParam(value = "page", defaultValue = "1") int page) {
        return postService.getPostList(page);
    }

    @Operation(summary = "게시글 페이지 수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 페이지 수 조회 성공")
    })
    @GetMapping("/page")
    public ResponseEntity<Integer> getPageCount() {
        return ResponseEntity.ok(postService.getPageCount());
    }

    @Operation(summary = "게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        PostDto postDto = postService.getPost(id, userService.getLoginUser());
        return ResponseEntity.ok(postDto);
    }

    @Operation(summary = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공")
    })
    @PostMapping("")
    public ResponseEntity<Long> createPost(@RequestBody PostCreateDto postCreateDto) {
        return ResponseEntity.created(null).body(postService.createPost(postCreateDto, userService.getLoginUser()));
    }

    @Operation(summary = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
            @ApiResponse(responseCode = "403", description = "작성자가 아닙니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePost(@PathVariable long id, @RequestBody PostUpdateDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto, userService.getLoginUser()));
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
            @ApiResponse(responseCode = "403", description = "작성자가 아닙니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지 설정 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @PostMapping(value = "/{id}/notice")
    public ResponseEntity<Void> noticePost(@PathVariable long id) {
        postService.togglePostNotice(id, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @PostMapping(value = "/{id}/like")
    public void likePost(@PathVariable long id) {
        postService.togglePostLike(id, userService.getLoginUser());
    }

    @Operation(summary = "좋아요 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @GetMapping(value = "/{id}/like")
    public List<LikeUserListDto> getLikeUsers(@PathVariable long id) {
        return postService.getLikeUsers(id, userService.getLoginUser());
    }
}
