package com.sparta.blog.domain.thumbsUp.service;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.blog.repository.BlogRepository;
import com.sparta.blog.domain.thumbsUp.dto.response.ThumbsUpResponseDto;
import com.sparta.blog.domain.thumbsUp.entity.ThumbsUp;
import com.sparta.blog.domain.thumbsUp.repository.ThumbsUpRepository;
import com.sparta.blog.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThumbsUpService {

    private final BlogRepository blogRepository;
    private final ThumbsUpRepository thumbsUpRepository;

    @Transactional
    public ThumbsUpResponseDto press(Long blogId, User user) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
            () -> new NullPointerException("해당 게시물이 존재하지 않습니다."));
        ThumbsUp thumbsUp = thumbsUpRepository.findByUserAndBlog(user, blog)
            .orElseGet(
                () -> ThumbsUp.builder()
                    .thumbsUp(false)
                    .user(user)
                    .blog(blog)
                    .build());
        Boolean toggle = thumbsUp.isThumbsUp();
        blog.isThumbsUp(toggle);
        thumbsUpRepository.save(thumbsUp);
        return new ThumbsUpResponseDto(thumbsUp.getThumbsUp());
    }


}
