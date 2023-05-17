package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.dto.LikeUserListDto;
import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.entity.LikePost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    int countByPostId(long postId);
    int countByPostIdAndUserId(long postId, long userId);
    void deleteByPostIdAndUserId(long postId, long userId);
    List<LikePost> findAllByPostId(long postId);
    List<LikePost> findAllByUserIdOrderByIdDesc(long userId, Pageable pageable);


    @Query("SELECT lp.post.id, COUNT(lp.post.id) AS count " +
            "FROM LikePost lp " +
            "GROUP BY lp.post.id " +
            "ORDER BY count DESC")
    List<Object[]> findTop5LikeCountPost(Pageable pageable);
}
