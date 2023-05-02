package com.gumi.enjoytrip.domain.post.service;

import com.gumi.enjoytrip.domain.post.dto.PostCreateDto;
import com.gumi.enjoytrip.domain.post.dto.PostDto;
import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.dto.PostUpdateDto;
import com.gumi.enjoytrip.domain.post.entity.LikePost;
import com.gumi.enjoytrip.domain.post.entity.Post;
import com.gumi.enjoytrip.domain.post.exception.InvalidUserException;
import com.gumi.enjoytrip.domain.post.exception.PostNotFoundException;
import com.gumi.enjoytrip.domain.post.repository.LikePostRepository;
import com.gumi.enjoytrip.domain.post.repository.PostRepository;
import com.gumi.enjoytrip.domain.user.entity.User;
import com.gumi.enjoytrip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikePostRepository likePostRepository;

    public List<PostListDto> listPost() {
        List<Post> postList = postRepository.findAll();
        List<PostListDto> postListDtoList = new ArrayList<>();
        for (Post post : postList) {
            String creatorNickname = post.getUser().getNickname();
            int likeCount = getLikeCount(post.getId());
            postListDtoList.add(toPostListDto(post, likeCount, creatorNickname));
        }

        return postListDtoList;
    }

    public void createPost(String title, String content, User user) {
        postRepository.save(Post.builder().title(title).content(content).user(user).build());
    }

    public PostDto detailPost(long postId) {
        Post post = postRepository.getOne(postId);
        long userId = post.getUser().getId();
        String creatorNickname = post.getUser().getNickname();
        boolean isLiked = getLikeStatus(postId, userId);
        int likeCount = getLikeCount(postId);

        increaseViewCount(postId);

        PostDto postDto = toPostDto(post, isLiked, likeCount, creatorNickname);
        return postDto;
    }

    public boolean getLikeStatus(long postId, long userId) {
        if( likePostRepository.countByPostIdAndUserId(postId, userId) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getLikeCount(long id) {
        return likePostRepository.countByPostId(id);
    }

    public void increaseViewCount(long id) {
        Post post = postRepository.getOne(id);
        post.update(Post.builder().views(post.getViews()+1).build());
        postRepository.save(post);
    }

    public Post getPost(long id) {
        return postRepository.getOne(id);
    }

    public void updatePost(long postId, String title, String content, User user) {
        if (getPost(postId) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (getPost(postId).getUser().getId() != user.getId()) {
            throw new InvalidUserException("작성자만 수정할 수 있습니다.");
        }

        Post post = postRepository.getOne(postId);
        post.update(Post.builder().title(title).content(content).build());
        postRepository.save(post);
    }

    public void deletePost(long postId, User user) {
        if (getPost(postId) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (getPost(postId).getUser().getId() != user.getId()) {
            throw new InvalidUserException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.deleteById(postId);
    }

    public void likePost(long postId, long userId) {
        if(likePostRepository.countByPostIdAndUserId(postId, userId) == 0) {
            likePostRepository.save(LikePost.builder().userId(userId).postId(postId).build());
        }
    }

    public void unlikePost(long postId, long userId) {
        likePostRepository.deleteById(likePostRepository.findByPostIdAndUserId(postId, userId).getId());
    }

    public void setNotice(long id, boolean isNotice, User user) {
        if (getPost(id) == null) {
            throw new PostNotFoundException("존재하지 않는 게시글입니다.");
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            throw new InvalidUserException("관리자만 공지사항을 설정 및 해제할 수 있습니다.");
        }

        Post post = postRepository.getOne(id);
        post.update(Post.builder().isNotice(isNotice).build());
        postRepository.save(post);
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
