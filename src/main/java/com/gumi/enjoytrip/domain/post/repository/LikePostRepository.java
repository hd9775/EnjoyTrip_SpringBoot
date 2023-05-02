package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.dto.LikePostCreateDto;
import com.gumi.enjoytrip.domain.post.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    int countByPostId(long postId);

    int countByPostIdAndUserId(long postId, long userId);

    void deleteById(long id);

    LikePost findByPostIdAndUserId(long postId, long userId);

}
