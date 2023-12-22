package com.sparta.blog.domain.thumbsUp.controller;

import com.sparta.blog.domain.thumbsUp.dto.response.ThumbsUpResponseDto;
import com.sparta.blog.domain.thumbsUp.service.ThumbsUpService;
import com.sparta.blog.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blogs/{blogId}/thumbs_up")
@RequiredArgsConstructor
public class ThumbsUpController {

    private final ThumbsUpService thumbsUpService;

    @PatchMapping
    public ResponseEntity<ThumbsUpResponseDto> press(
        @PathVariable(name = "blogId") Long blogId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ThumbsUpResponseDto responseDto = thumbsUpService.press(blogId,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
