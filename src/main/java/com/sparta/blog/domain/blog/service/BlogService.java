package com.sparta.blog.domain.blog.service;

import com.sparta.blog.domain.blog.dto.request.CreateBlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public CreateBlogResponseDto create(User user, CreateBlogRequestDto requestDto) {
        Blog blog = Blog.builder()
            .title(requestDto.getTitle())
            .text(requestDto.getText())
            .user(user)
            .build();
        blogRepository.save(blog);
        return new CreateBlogResponseDto(blog, user);
    }

    public SelectBlogResponseDto getBlog(Long blogId) {
        Blog blog = checkBlogId(blogId);
        return new SelectBlogResponseDto(blog);
    }


    ////////////////////////////////////////////////////////////////////////
    public Blog checkBlogId(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
    }
    ////////////////////////////////////////////////////////////////////////
}
