package com.sparta.blog.domain.comment.dto.response;

import com.sparta.blog.domain.comment.entity.Comment;
import com.sparta.blog.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateCommentResponseDto {

    private Long id;
    private String username;
    private String text;
    private LocalDateTime date;

    public CreateCommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.username = user.getUsername();
        this.text = comment.getText();
        this.date = comment.getCreatedAt();
    }

}
