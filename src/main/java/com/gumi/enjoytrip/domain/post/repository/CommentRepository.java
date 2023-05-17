package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.dto.CommentListDto;
import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(long postId);

    List<Comment> findAllByUserIdOrderByIdDesc(long userId, Pageable pageable);
}
