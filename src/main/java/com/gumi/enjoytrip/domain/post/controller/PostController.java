package com.gumi.enjoytrip.domain.post.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gumi.enjoytrip.domain.post.dto.*;
import com.gumi.enjoytrip.domain.post.entity.Post;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
import com.gumi.enjoytrip.domain.post.exception.PostNotFoundException;
import com.gumi.enjoytrip.domain.post.service.PostService;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Operation(summary = "게시글 목록")
    @GetMapping("/")
    public ResponseEntity<List<PostListDto>> listPost() {
        List<PostListDto> postListDtoList = postService.listPost();

        return ResponseEntity.ok(postListDtoList);
    }

    @Operation(summary = "게시글 조회")
    @PostMapping("/{postId}")
    public ResponseEntity<PostDto> detailPost(@PathVariable(value = "postId") long postId) {
        PostDto postDto = postService.detailPost(postId);
        return ResponseEntity.ok(postDto);
    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateDto postCreateDto) {
        postService.createPost(postCreateDto.getTitle(), postCreateDto.getContent(), userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@RequestBody PostUpdateDto postDto) {
        System.out.println(postDto.getId());
        postService.updatePost(postDto.getId(), postDto.getTitle(), postDto.getContent(), userService.getLoginUser());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "postId") long postId) {
        postService.deletePost(postId, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지 설정")
    @PostMapping(value = "/{postId}/notice")
    public ResponseEntity<Void> setNotice(@PathVariable(value = "postId") long id, @RequestBody Map<String, String> requestBody) {
        postService.setNotice(id, Boolean.parseBoolean(requestBody.get("isNotice")), userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요")
    @PostMapping(value = "/{postId}/like")
    public ResponseEntity<Void> likePost(@RequestBody LikePostDto likePostDto) {
        postService.likePost(likePostDto.getPostId(), likePostDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 취소")
    @PostMapping(value = "/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@RequestBody LikePostDto likePostDto) {
        postService.unlikePost(likePostDto.getPostId(), likePostDto.getUserId());
        return ResponseEntity.ok().build();
    }

    public PostDto toPostDto(Post post, boolean isLiked, int likeCount, String creatorNickname) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setViews(post.getViews());
        postDto.setIsLiked(isLiked);
        postDto.setIsNotice(post.isNotice());
        postDto.setLikeCount(likeCount);
        postDto.setCreatorId(post.getUser().getId());
        postDto.setCreatorNickname(creatorNickname);
        postDto.setCreatedAt(convertLocalDateTimeToString(post.getCreatedAt()));
        return postDto;
    }

    public PostDto toPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setViews(post.getViews());
        postDto.setIsNotice(post.isNotice());
        postDto.setCreatorId(post.getUser().getId());
        postDto.setCreatedAt(convertLocalDateTimeToString(post.getCreatedAt()));
        return postDto;
    }

    public String convertLocalDateTimeToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.toString().formatted(formatter);
    }

}
