package com.sparta.blog.domain.user.service;

import com.sparta.blog.domain.user.dto.request.SignupRequestDto;
import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        checkUsername(username);
        checkPassword(password,username);
        User user = User.builder()
            .username(username)
            .password(password)
            .build();
        userRepository.save(user);
    }

    ///////////////////////////////////////////////////////////////////////////
    private void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("동일한 username이 존재 합니다.");
        }
    }

    private void checkPassword(String password, String username) {
        if (password.contains(username)){
            throw new IllegalArgumentException("username과 일치하지 않게 입력 해주세요");
        }
    }
    ///////////////////////////////////////////////////////////////////////////
}
