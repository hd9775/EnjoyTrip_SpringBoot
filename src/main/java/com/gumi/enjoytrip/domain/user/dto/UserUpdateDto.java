package com.gumi.enjoytrip.domain.user.dto;

public class UserUpdateDto {
    private long id;
    private String nickname;

    public UserUpdateDto() {
    }

    public UserUpdateDto(long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
