package com.sparta.blog.domain.comment.dto.response;

import com.sparta.blog.domain.comment.entity.Comment;
import com.sparta.blog.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateCommentResponseDto {

    private Long id;
    private String username;
    private String text;
    private Boolean thumbsUp;
    private LocalDateTime date;

    public UpdateCommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.username = user.getUsername();
        this.text = comment.getText();
        this.thumbsUp = comment.getThumbsUp();
        this.date = comment.getModifiedAt();
    }

}
