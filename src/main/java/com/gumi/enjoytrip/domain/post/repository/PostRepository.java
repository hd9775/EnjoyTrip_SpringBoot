package com.gumi.enjoytrip.domain.post.repository;

import com.gumi.enjoytrip.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void increaseViews(long id);
}
