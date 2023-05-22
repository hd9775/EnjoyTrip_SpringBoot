package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(long postId);

    List<Comment> findAllByUserIdOrderByIdDesc(long userId, Pageable pageable);

    int countByPostId(long postId);

    int countByUserId(long id);

    @Query("SELECT COUNT(DISTINCT c.post.id) FROM Comment c WHERE c.user.id = :userId")
    int countByUserIdGroupByPostId(long userId);
}
