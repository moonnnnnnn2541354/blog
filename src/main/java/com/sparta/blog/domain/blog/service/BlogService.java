package com.sparta.blog.domain.blog.service;

import com.sparta.blog.domain.blog.dto.request.CreateBlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public CreateBlogResponseDto create(User user, CreateBlogRequestDto requestDto) {
        Blog blog = Blog.builder()
            .title(requestDto.getTitle())
            .text(requestDto.getText())
            .user(user)
            .build();
        blogRepository.save(blog);
        return new CreateBlogResponseDto(blog,user);
    }


    ////////////////////////////////////////////////////////////////////////
    public void checkUser(User user) {

    }
    ////////////////////////////////////////////////////////////////////////
}
