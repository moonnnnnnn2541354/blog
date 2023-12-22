package com.sparta.blog.domain.comment.service;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.comment.dto.request.CreateCommentRequestDto;
import com.sparta.blog.domain.comment.dto.request.UpdateCommentRequestDto;
import com.sparta.blog.domain.comment.dto.response.CreateCommentResponseDto;
import com.sparta.blog.domain.comment.dto.response.UpdateCommentResponseDto;
import com.sparta.blog.domain.comment.entity.Comment;
import com.sparta.blog.domain.comment.repository.CommentRepository;
import com.sparta.blog.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponseDto create(User user, Long blogId,
        CreateCommentRequestDto requestDto) {

        Blog blog = checkBlog(blogId);
        Comment comment = Comment.builder()
            .text(requestDto.getText())
            .thumbsUp(false)
            .user(user)
            .blog(blog)
            .build();
        commentRepository.save(comment);
        return new CreateCommentResponseDto(comment, user);
    }

    @Transactional
    public UpdateCommentResponseDto update(User user, Long blogId,
        Long commentId, UpdateCommentRequestDto requestDto) {

        Comment comment = checkComment(commentId);
        Blog blog = checkBlog(blogId);
        checkUser(comment, user);
        comment.update(requestDto);
        return new UpdateCommentResponseDto(comment, user);

    }


    //////////////////////////////////////////////////////////////
    private Blog checkBlog(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
            () -> new NullPointerException("해당 블로그가 존재하지 않습니다."));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
    }

    private void checkUser(Comment comment, User user) {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 유저정보가 일치하지 않습니다.");
        }
    }

    //////////////////////////////////////////////////////////////
}
