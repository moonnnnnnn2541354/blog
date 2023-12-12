package com.sparta.blog.global.security;

import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
        return new UserDetailsImpl(user);
    }

    public User userById(Long id) {
        return userRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("회원정보가 일치하지 않습니다."));
    }
}
