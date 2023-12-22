package com.sparta.blog.domain.blog.service;

import com.sparta.blog.domain.blog.dto.request.BlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.BlogListResponseDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.UpdateBlogResponseDto;
import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.comment.dto.response.CommentResponseDto;
import com.sparta.blog.domain.comment.entity.Comment;
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
    public CreateBlogResponseDto create(User user, BlogRequestDto requestDto) {
        Blog blog = Blog.builder()
            .title(requestDto.getTitle())
            .text(requestDto.getText())
            .user(user)
            .thumbsUpPoint(0L)
            .build();
        blogRepository.save(blog);
        return new CreateBlogResponseDto(blog, user);
    }

    public SelectBlogResponseDto getBlog(Long blogId) {
        Blog blog = checkBlogId(blogId);
        List<CommentResponseDto> commentList = getCommentList(blog);
        return new SelectBlogResponseDto(blog, commentList);
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

    @Transactional
    public UpdateBlogResponseDto update(Long blogId, User user, BlogRequestDto requestDto) {
        Blog blog = checkBlogId(blogId);
        checkUser(blog, user);
        blog.update(requestDto);
        return new UpdateBlogResponseDto(blog);
    }

    @Transactional
    public void delete(Long blogId, User user) {
        Blog blog = checkBlogId(blogId);
        checkUser(blog, user);
        blogRepository.delete(blog);
    }


    ////////////////////////////////////////////////////////////////////////
    public Blog checkBlogId(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
    }

    public void checkUser(Blog blog, User user) {
        if (!blog.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 게시물과 유저정보가 일치하지 않습니다.");
        }
    }

    public List<CommentResponseDto> getCommentList(Blog blog) {
        List<CommentResponseDto> responseDtoList = new ArrayList<>();
        List<Comment> commentList = blog.getCommentList();
        commentList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        for (Comment comment : commentList) {
            responseDtoList.add(new CommentResponseDto(comment));
        }
        return responseDtoList;
    }

    ////////////////////////////////////////////////////////////////////////
}
