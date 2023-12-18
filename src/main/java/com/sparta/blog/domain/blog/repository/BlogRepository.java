package com.sparta.blog.domain.blog.repository;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> findAllByUserOrderByModifiedAtDesc(User user);

    List<Blog> findAllByUserNotOrderByModifiedAtDesc(User user);
}
