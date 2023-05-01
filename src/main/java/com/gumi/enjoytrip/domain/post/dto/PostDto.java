package com.gumi.enjoytrip.domain.post.dto;

public class PostDto {
    private long id;
    private String title;
    private String content;
    private int views;
    private boolean isLiked;
    private boolean isNotice;
    private int likeCount;
    private long creatorId;
    private String creatorNickname;
    private String createdAt;

    public PostDto(long id, String title, String content, int views, boolean isLiked, boolean isNotice, int likeCount, long creatorId, String creatorNickname, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.isLiked = isLiked;
        this.isNotice = isNotice;
        this.likeCount = likeCount;
        this.creatorId = creatorId;
        this.creatorNickname = creatorNickname;
        this.createdAt = createdAt;
    }

    public PostDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(boolean isNotice) {
        this.isNotice = isNotice;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
