package com.gumi.enjoytrip.domain.post.controller;

import com.gumi.enjoytrip.domain.post.dto.*;
import com.gumi.enjoytrip.domain.post.entity.Post;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
import com.gumi.enjoytrip.domain.post.exception.PostNotFoundException;
import com.gumi.enjoytrip.domain.post.service.PostService;
import com.gumi.enjoytrip.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;
    private UserService userService;

    @Operation(summary = "게시글 목록")
    @GetMapping("/")
    public ResponseEntity<List<PostListDto>> listPost() {
        List<Post> postList = postService.listPost();
        List<PostListDto> postListDtoList = new ArrayList<>();
        for (Post post : postList) {
            String creatorNickname = post.getUser().getNickname();
            int likeCount = postService.getLikeCount(post.getId());
            postListDtoList.add(toPostListDto(post, likeCount, creatorNickname));
        }

        return ResponseEntity.ok(postListDtoList);
    }

    @Operation(summary = "게시글 조회")
    @PostMapping("/{postId}}")
    public ResponseEntity<PostDto> detailPost(@PathVariable(value = "postId") long postId, @RequestParam(value = "userId") long userId) {
        Post post = postService.detailPost(postId);
        String creatorNickname = post.getUser().getNickname();
        boolean isLiked = postService.getLikeStatus(postId, userId);
        int likeCount = postService.getLikeCount(postId);
        postService.increaseViewCount(postId);

        return ResponseEntity.ok(toPostDto(post, isLiked, likeCount, creatorNickname));
    }

    @Operation(summary = "게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<Void> createPost(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, @RequestParam(value = "id") long createrId) {
        PostCreateDto post = new PostCreateDto(title, content, createrId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}}")
    public ResponseEntity<Void> updatePost(@PathVariable(value = "postId") long postId, @RequestParam(value = "id") long userId, PostUpdateDto postUpdateDto) {
        if (postService.getPost(postId) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (postService.getPost(postId).getUser().getId() != userId) {
            throw new InvalidUserException("작성자만 수정할 수 있습니다.");
        }
        postService.updatePost(postId, postUpdateDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "postId") long postId, @RequestParam(value = "id") long userId) {
        if (postService.getPost(postId) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (postService.getPost(postId).getUser().getId() != userId) {
            throw new InvalidUserException("작성자만 삭제할 수 있습니다.");
        }
        postService.deletePost(postId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지 설정")
    @PostMapping(value = "/{postId}/notice")
    public ResponseEntity<Void> setNotice(@PathVariable(value = "postId") long id, boolean isNotice, long userId) {
        if (postService.getPost(id) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (!userService.getUser(userId).getRole().equals("ADMIN")) {
            throw new InvalidUserException("관리자만 공지사항을 설정 및 해제할 수 있습니다.");
        }
        postService.setNotice(id, isNotice);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요")
    public ResponseEntity<Void> likePost(long id, long userId) {
        postService.likePost(id, userId);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "좋아요 취소")
    public ResponseEntity<Void> unlikePost(long id, long userId) {
        postService.unlikePost(id, userId);

        return ResponseEntity.ok().build();
    }




    public PostListDto toPostListDto(Post post, int likeCount, String creatorName) {
        PostListDto postListDto = new PostListDto();
        postListDto.setId(post.getId());
        postListDto.setTitle(post.getTitle());
        postListDto.setViews(post.getViews());
        postListDto.setIsNotice(post.isNotice());
        postListDto.setLikeCount(likeCount);
        postListDto.setCreatorId(post.getUser().getId());
        postListDto.setCreatorNickname(creatorName);
        postListDto.setCreatedAt(convertLocalDateTimeToString(post.getCreatedAt()));
        return postListDto;
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

    public String convertLocalDateTimeToString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.toString().formatted(formatter);
    }



}
