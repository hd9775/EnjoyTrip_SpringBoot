package com.gumi.enjoytrip.domain.post.dto;

public class LikeUserListDto {
    private long creatorId;
    private String creatorNickname;

    public LikeUserListDto(long id, long creatorId, String creatorNickname) {
        this.creatorId = creatorId;
        this.creatorNickname = creatorNickname;
    }

    public LikeUserListDto() {

    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }
}
