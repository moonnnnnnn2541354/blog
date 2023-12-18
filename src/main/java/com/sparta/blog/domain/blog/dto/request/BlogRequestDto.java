package com.sparta.blog.domain.blog.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BlogRequestDto {
    @Pattern(regexp = "^.{1,500}$")
    private String title;

    @Pattern(regexp = "^.{1,5000}$")
    private String text;

}
