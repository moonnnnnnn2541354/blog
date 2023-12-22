package com.sparta.blog.domain.blog.dto.response;

import com.sparta.blog.domain.blog.entity.Blog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PagingResponseDto {

    private String title;
    private String username;
    private LocalDateTime date;

    public PagingResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.username = blog.getUser().getUsername();
        this.date = blog.getCreatedAt();
    }


}
