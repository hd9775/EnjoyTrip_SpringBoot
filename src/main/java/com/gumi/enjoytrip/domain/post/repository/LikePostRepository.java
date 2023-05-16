package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    int countByPostId(long postId);
    int countByPostIdAndUserId(long postId, long userId);
    void deleteByPostIdAndUserId(long postId, long userId);
    List<LikePost> findAllByPostId(long postId);
}
