package com.sparta.blog.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.blog.domain.user.dto.request.LoginRequestDto;
import com.sparta.blog.global.entity.UserRoleEnum;
import com.sparta.blog.global.jwt.JwtUtil;
import com.sparta.blog.global.jwt.entity.JwtEntity;
import com.sparta.blog.global.jwt.repository.JwtRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final JwtRepository jwtRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtRepository jwtRepository) {

        this.jwtUtil = jwtUtil;
        this.jwtRepository = jwtRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getUsername(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authentication) {

        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        UserRoleEnum roleEnum = ((UserDetailsImpl) authentication.getPrincipal()).getUser()
            .getRole();

        String accessToken = jwtUtil.createAccessToken(username, roleEnum);
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, accessToken);

        JwtEntity jwt = JwtEntity.builder()
            .refreshToken(refreshToken.substring(7))
            .user(((UserDetailsImpl) authentication.getPrincipal()).getUser())
            .build();
        jwtRepository.save(jwt);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
