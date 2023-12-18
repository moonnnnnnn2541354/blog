package com.sparta.blog.domain.blog.dto.response;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateBlogResponseDto {

    private Long id;
    private String username;
    private String title;
    private String text;
    private LocalDateTime createAd;

    public CreateBlogResponseDto(Blog blog, User user) {
        this.id = blog.getId();
        this.username = user.getUsername();
        this.title = blog.getTitle();
        this.text = blog.getText();
        this.createAd = blog.getCreatedAt();
    }

}
