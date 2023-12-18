package com.sparta.blog.global.jwt.repository;

import com.sparta.blog.global.jwt.entity.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<JwtEntity, Long> {

    JwtEntity findByRefreshToken(String refreshToken);

    JwtEntity findByUserId(Long id);
}
