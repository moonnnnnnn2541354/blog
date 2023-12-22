package com.sparta.blog.domain.comment.dto.response;

import com.sparta.blog.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String text;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.text = comment.getText();
        this.date = comment.getModifiedAt();
    }

}
