package com.sparta.blog.domain.comment.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @Pattern(regexp = "^.{1,50}$")
    private String text;

}
