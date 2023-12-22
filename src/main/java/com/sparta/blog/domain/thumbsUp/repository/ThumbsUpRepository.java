package com.sparta.blog.domain.thumbsUp.repository;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.thumbsUp.entity.ThumbsUp;
import com.sparta.blog.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbsUpRepository extends JpaRepository<ThumbsUp, Long> {

    Optional<ThumbsUp> findByUserAndBlog(User user, Blog blog);
}
