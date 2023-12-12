package com.sparta.blog.global.security;

import static com.sparta.blog.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.sparta.blog.global.jwt.JwtUtil.BEARER_PREFIX;
import static com.sparta.blog.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.sparta.blog.global.jwt.JwtUtil;
import com.sparta.blog.global.jwt.entity.JwtEntity;
import com.sparta.blog.global.jwt.repository.JwtRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtRepository jwtRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, JwtRepository jwtRepository,
        UserDetailsServiceImpl userDetailsService) {

        this.jwtUtil = jwtUtil;
        this.jwtRepository = jwtRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {

        String accessToken = jwtUtil.getJwtFromHeader(request,ACCESS_TOKEN_HEADER);
        if (StringUtils.hasText(accessToken)&&!jwtUtil.validateToken(accessToken)) {
            String refreshToken = jwtUtil.getJwtFromHeader(request,REFRESH_TOKEN_HEADER);

            if (StringUtils.hasText(refreshToken)&& jwtUtil.validateToken(refreshToken)) {
                JwtEntity jwtEntity = jwtRepository.findByRefreshToken(refreshToken);
                accessToken = jwtUtil.createAccessToken(
                    jwtEntity.getUser().getUsername(),
                    jwtEntity.getUser().getRole())
                    .split(" ")[1].trim();
                response.addHeader(ACCESS_TOKEN_HEADER,BEARER_PREFIX + accessToken);
            }
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        chain.doFilter(request,response);
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}
