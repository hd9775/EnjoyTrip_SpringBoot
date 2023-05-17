package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.dto.PostListDto;
import com.gumi.enjoytrip.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("update Post p set p.views = p.views + 1 where p.id = :id")
    void increaseViews(long id);

    List<Post> findAllByTitleContainingIgnoreCaseOrderByIsNoticeDescIdDesc(String keyword, Pageable pageable);

    List<Post> findAllByUserIdOrderByIdDesc(long userId, Pageable pageable);

    List<Post> findAllByIsNoticeTrueOrderByCreatedAtDesc(Pageable pageable);
}
