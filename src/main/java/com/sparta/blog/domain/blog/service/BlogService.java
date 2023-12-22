package com.sparta.blog.domain.blog.service;

import com.sparta.blog.domain.blog.dto.request.BlogRequestDto;
import com.sparta.blog.domain.blog.dto.response.BlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.CreateBlogResponseDto;
import com.sparta.blog.domain.blog.dto.response.PagingResponseDto;
import com.sparta.blog.domain.blog.dto.response.SelectBlogResponseDto;
import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.comment.dto.response.CommentResponseDto;
import com.sparta.blog.domain.comment.entity.Comment;
import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.global.entity.UserRoleEnum;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<PagingResponseDto> getBlogList(
        User user, int page, int size) {

        Pageable pageable = PageRequest.of(page,size);

        UserRoleEnum roleEnum = user.getRole();
        Page<Blog> blogList;
        if (roleEnum == UserRoleEnum.USER) {
            blogList = blogRepository.findAllByUserOrderByCreatedAtDesc(user,pageable);
        } else {
            blogList = blogRepository.findAll(pageable);
        }
        return blogList.map(PagingResponseDto::new);
    }

    public List<BlogResponseDto> getMyBlogList(User user) {
        List<Blog> blogs = blogRepository.findAllByUserOrderByCreatedAtDesc(user);
        List<BlogResponseDto> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            blogList.add(new BlogResponseDto(blog));
        }
        return blogList;
    }

    public List<BlogResponseDto> getUserBlogList(User user) {
        List<Blog> blogs = blogRepository.findAllByUserNotOrderByCreatedAtDesc(user);
        List<BlogResponseDto> blogList = new ArrayList<>();
        for (Blog blog : blogs) {
            blogList.add(new BlogResponseDto(blog));
        }
        return blogList;
    }

    @Transactional
    public BlogResponseDto update(Long blogId, User user, BlogRequestDto requestDto) {
        Blog blog = checkBlogId(blogId);
        checkUser(blog, user);
        blog.update(requestDto);
        return new BlogResponseDto(blog);
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
