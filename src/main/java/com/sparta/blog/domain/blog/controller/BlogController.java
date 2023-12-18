package com.sparta.blog.domain.blog.controller;

import com.sparta.blog.domain.blog.dto.request.BlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.BlogListResponseDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.UpdateBlogResponseDto;
import com.sparta.blog.domain.blog.service.BlogService;
import com.sparta.blog.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        @Valid @RequestBody BlogRequestDto requestDto) {

        CreateBlogResponseDto responseDto = blogService.create(userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<SelectBlogResponseDto> getBlog(
        @PathVariable(name = "blogId") Long blogId) {

        SelectBlogResponseDto responseDto = blogService.getBlog(blogId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/every")
    public ResponseEntity<List<BlogListResponseDto>> getBlogList() {
        List<BlogListResponseDto> responseDtos = blogService.getBlogList();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/my-blogs")
    public ResponseEntity<List<BlogListResponseDto>> getMyBlogList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BlogListResponseDto> responseDtos = blogService.getMyBlogList(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @GetMapping("/user-blogs")
    public ResponseEntity<List<BlogListResponseDto>> getUserBlogList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BlogListResponseDto> responseDtos = blogService.getUserBlogList(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<UpdateBlogResponseDto> updateBlog(
        @PathVariable(name = "blogId") Long blogId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody BlogRequestDto requestDto) {

        UpdateBlogResponseDto responseDto = blogService.update(blogId, userDetails.getUser(),
            requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<String> delete(
        @PathVariable(name = "blogId") Long blogId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        blogService.delete(blogId,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("해당 게시물이 삭제 되었습니다.");
    }


}
