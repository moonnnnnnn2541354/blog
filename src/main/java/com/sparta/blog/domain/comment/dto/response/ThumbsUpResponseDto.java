package com.sparta.blog.domain.comment.dto.response;

import lombok.Getter;

@Getter
public class ThumbsUpResponseDto {

    private Boolean thumbsUp;

    public ThumbsUpResponseDto(Boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

}
