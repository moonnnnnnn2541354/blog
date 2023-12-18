package com.sparta.blog.domain.blog.dto.response;

import com.sparta.blog.domain.blog.entity.Blog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BlogListResponseDto {

    private Long id;
    private String username;
    private String title;
    private String text;
    private LocalDateTime date;

    public BlogListResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUser().getUsername();
        this.title = blog.getTitle();
        this.text = blog.getText();
        this.date = blog.getModifiedAt();
    }

}
