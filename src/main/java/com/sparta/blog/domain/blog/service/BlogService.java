package com.sparta.blog.domain.blog.service;

import com.sparta.blog.domain.blog.dto.request.CreateBlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.BlogListResponseDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
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

    public List<BlogListResponseDto> getBlogList() {
        List<Blog> blogs = blogRepository.findAllByOrderByModifiedAtDesc();
        List<BlogListResponseDto> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            blogList.add(new BlogListResponseDto(blog));
        }
        return blogList;
    }

    public List<BlogListResponseDto> getMyBlogList(User user) {
        List<Blog> blogs = blogRepository.findAllByUserOrderByModifiedAtDesc(user);
        List<BlogListResponseDto> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            blogList.add(new BlogListResponseDto(blog));
        }
        return blogList;
    }

    public List<BlogListResponseDto> getUserBlogList(User user) {
        List<Blog> blogs = blogRepository.findAllByUserNotOrderByModifiedAtDesc(user);
        List<BlogListResponseDto> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            blogList.add(new BlogListResponseDto(blog));
        }
        return blogList;
    }


    ////////////////////////////////////////////////////////////////////////
    public Blog checkBlogId(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
    }

    ////////////////////////////////////////////////////////////////////////
}
