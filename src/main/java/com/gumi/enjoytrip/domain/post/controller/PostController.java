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
    public List<PostListDto> getPosts(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return postService.getPostList(page, keyword);
    }

    @Operation(summary = "게시글 페이지 수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 페이지 수 조회 성공")
    })
    @GetMapping("/page")
    public ResponseEntity<Integer> getPageCount(@RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return ResponseEntity.ok(postService.getPageCount(keyword));
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
        return postService.getLikeUserList(id);
    }

    @Operation(summary = "댓글 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @GetMapping(value = "/{id}/comments")
    public List<CommentListDto> getComments(@PathVariable long id) {
        return postService.getCommentList(id);
    }

    @Operation(summary = "댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @PostMapping(value = "/{id}/comments")
    public ResponseEntity<Long> createComment(@PathVariable long id, @RequestBody CommentCreateDto commentCreateDto) {
        return ResponseEntity.created(null).body(postService.createComment(id, commentCreateDto, userService.getLoginUser()));
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "작성자가 아닙니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 게시글입니다."),
    })
    @DeleteMapping(value = "/{postId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long postId, @PathVariable long id) {
        postService.deleteComment(id, userService.getLoginUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "본인 작성글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "/profile")
    public List<PostListDto> getMyPost(@RequestParam(value = "type", defaultValue = "") String type, @RequestParam(value = "page", defaultValue = "1") int page) {
        if(type.equals("post")) {
            return postService.getMyPost(page, userService.getLoginUser());
        } else if(type.equals("comment")) {
            return postService.getPostListByMyCommentId(page, userService.getLoginUser());
        } else if(type.equals("like")) {
            return postService.getPostListByMyLikePost(page, userService.getLoginUser());
        } else {
            return null;
        }
    }

    @Operation(summary = "홈화면 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "/home")
    public List<PostTitleListDto> getHomePosts(@RequestParam(value = "type", defaultValue = "") String type) {
        if(type.equals("notice")) {
            return postService.getLatestNotice();
        } else if(type.equals("hot")) {
            return postService.getTopLikePost();
        } else if(type.equals("post")) {
            return postService.getLatestPost();
        }else {
            return null;
        }
    }

}
