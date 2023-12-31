package com.sparta.blog.domain.comment.controller;

import com.sparta.blog.domain.comment.dto.request.CreateCommentRequestDto;
import com.sparta.blog.domain.comment.dto.request.UpdateCommentRequestDto;
import com.sparta.blog.domain.comment.dto.response.CreateCommentResponseDto;
import com.sparta.blog.domain.comment.dto.response.UpdateCommentResponseDto;
import com.sparta.blog.domain.comment.service.CommentService;
import com.sparta.blog.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs/{blogId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> create(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "blogId") Long blogId,
        @Valid @RequestBody CreateCommentRequestDto requestDto) {

        CreateCommentResponseDto responseDto = commentService.create(userDetails.getUser(), blogId,
            requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> update(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "blogId") Long blogId,
        @PathVariable(name = "commentId") Long commentId,
        @Valid @RequestBody UpdateCommentRequestDto requestDto) {

        UpdateCommentResponseDto responseDto = commentService.update(userDetails.getUser(), blogId,
            commentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "blogId") Long blogId,
        @PathVariable(name = "commentId") Long commentId) {

        commentService.delete(userDetails.getUser(), blogId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body("해당 게시물이 삭제 되었습니다.");

    }
}

