package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("update Post p set p.views = p.views + 1 where p.id = :id")
    void increaseViews(long id);

    List<Post> findAllByTitleContainingIgnoreCaseOrderByIsNoticeDescIdDesc(String keyword, Pageable pageable);

    List<Post> findAllByUserIdOrderByIdDesc(long userId, Pageable pageable);

    List<Post> findAllByIsNoticeTrueOrderByIdDesc(Pageable pageable);

    int countByTitleContainingIgnoreCase(String keyword);

    List<Post> findAllByIsNoticeFalseOrderByIdDesc(Pageable pageable);

    int countByUserId(long userId);

    @Query("select p from Post p join Comment c on p.id = c.post.id where c.user.id = :userId order by p.id desc")
    List<Post> findByUserCommentsUserOrderByUserCommentsPostIdDesc(Long userId, Pageable pageable);

    @Query("select p from Post p join LikePost lp on p.id = lp.post.id where lp.user.id = :userId order by p.id desc")
    List<Post> findByUserLikesUserOrderByUserLikesPostIdDesc(Long userId, Pageable pageable);
}
