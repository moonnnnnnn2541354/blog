package com.sparta.blog.domain.thumbsUp.dto.response;

import lombok.Getter;

@Getter
public class ThumbsUpResponseDto {

    private Boolean thumbsUp;

    public ThumbsUpResponseDto(Boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

}
