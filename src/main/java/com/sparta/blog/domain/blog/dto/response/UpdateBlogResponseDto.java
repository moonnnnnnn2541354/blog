package com.sparta.blog.domain.blog.dto.response;

import com.sparta.blog.domain.blog.entity.Blog;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateBlogResponseDto {

    private Long id;
    private String username;
    private String title;
    private String text;
    private Long thumbsUpPoint;
    private LocalDateTime date;

    public UpdateBlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUser().getUsername();
        this.title = blog.getTitle();
        this.text = blog.getText();
        this.thumbsUpPoint = blog.getThumbsUpPoint();
        this.date = blog.getModifiedAt();
    }

}
