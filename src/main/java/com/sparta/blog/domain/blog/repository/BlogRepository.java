package com.sparta.blog.domain.blog.repository;

import com.sparta.blog.domain.blog.entity.Blog;
import com.sparta.blog.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByOrderByModifiedAtDesc();

    List<Blog> findAllByUserOrderByCreatedAtDesc(User user);

    List<Blog> findAllByUserNotOrderByCreatedAtDesc(User user);

    Page<Blog> findAllByUser(User user, Pageable pageable);

    Page<Blog> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
