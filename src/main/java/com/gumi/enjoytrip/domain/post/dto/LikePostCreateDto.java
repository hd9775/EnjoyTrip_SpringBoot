package com.gumi.enjoytrip.domain.post.dto;

import com.gumi.enjoytrip.domain.post.entity.LikePost;

public class LikePostCreateDto {
    private long userId;
    private long postId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getpostId() {
        return postId;
    }

    public void setpostId(long postId) {
        this.postId = postId;
    }

    public LikePost toEntity() {
        return LikePost.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}
