package com.gumi.enjoytrip.domain.post.dto;

public class PostCreateDto {
    private String title;
    private String content;
    private long creatorId;

    public PostCreateDto() {
    }

    public PostCreateDto(String title, String content, long creatorId) {
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
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

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}
