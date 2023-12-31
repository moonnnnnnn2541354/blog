package com.sparta.blog.domain.blog.dto.response;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.comment.dto.response.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class SelectBlogResponseDto {

    private Long id;
    private String username;
    private String title;
    private String text;
    private Long thumbsUpPoint;
    private LocalDateTime date;
    private List<CommentResponseDto> commentList;

    public SelectBlogResponseDto(Blog blog, List<CommentResponseDto> commentList) {
        this.id = blog.getId();
        this.username = blog.getUser().getUsername();
        this.title = blog.getTitle();
        this.text = blog.getText();
        this.thumbsUpPoint = blog.getThumbsUpPoint();
        this.date = blog.getCreatedAt();
        this.commentList = commentList;
    }


}
