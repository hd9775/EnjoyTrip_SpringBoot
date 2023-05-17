package com.gumi.enjoytrip.domain.post.dto;

import com.gumi.enjoytrip.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUpdateDto {
    private long id;
    private String title;
    private String content;

    public Post toEntity(boolean isNotice) {
        return Post.builder()
                .title(title)
                .content(content)
                .isNotice(isNotice)
                .build();
    }
}
