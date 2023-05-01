package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.dto.PostDto;
import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.dto.PostUpdateDto;
import com.gumi.enjoytrip.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> listPost();

    void createPost();

    Post detailPost(long id);

    Post getPost(long id);

    void updatePost(long postId, PostUpdateDto postUpdateDto);

    void deletePost(long postId);

    void likePost(long id, long userId);

    void unlikePost(long id, long userId);

    void setNotice(long id, boolean isNotice);

    void increaseViewCount(long id);

    boolean getLikeStatud(long postId, long userId);

    int getLikeCount(long id);
}
