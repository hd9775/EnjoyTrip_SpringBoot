package com.gumi.enjoytrip.domain.post.dto;

import com.gumi.enjoytrip.domain.post.entity.Post;
import com.gumi.enjoytrip.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateDto {
    private String title;
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
