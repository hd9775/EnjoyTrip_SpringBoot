package com.gumi.enjoytrip.domain.post.dto;

public class LikePostDto {

    private long id;
    private long userId;
    private long postId;
    private String createAt;

    public LikePostDto(long id, long userId, long postId, String createAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.createAt = createAt;
    }

    public LikePostDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
