package com.sparta.blog.domain.blog.controller;

import com.sparta.blog.domain.blog.dto.request.CreateBlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.service.BlogService;
import com.sparta.blog.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<CreateBlogResponseDto> create(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody CreateBlogRequestDto requestDto) {

        CreateBlogResponseDto responseDto = blogService.create(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<SelectBlogResponseDto> getBlog(
        @PathVariable(name = "blogId") Long blogId) {

        SelectBlogResponseDto responseDto = blogService.getBlog(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
