package com.sparta.blog.domain.user.service;

import com.sparta.blog.domain.user.dto.request.SignupRequestDto;
import com.sparta.blog.domain.user.entity.User;
import com.sparta.blog.domain.user.repository.UserRepository;
import com.sparta.blog.global.entity.UserRoleEnum;
import com.sparta.blog.global.jwt.entity.JwtEntity;
import com.sparta.blog.global.jwt.repository.JwtRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtRepository jwtRepository;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = BCrypt.hashpw(passwordEncoder.encode(requestDto.getPassword()), BCrypt.gensalt());
        checkUsername(username);
        checkPassword(password, username);
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 일치하지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = User.builder()
            .username(username)
            .password(password)
            .role(role)
            .build();
        userRepository.save(user);
    }

    @Transactional
    public void logout(User user) {
        JwtEntity refreshToken = jwtRepository.findByUserId(user.getId());
        jwtRepository.delete(refreshToken);
    }

    ///////////////////////////////////////////////////////////////////////////
    private void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("동일한 username이 존재 합니다.");
        }
    }

    private void checkPassword(String password, String username) {
        if (password.contains(username)) {
            throw new IllegalArgumentException("username과 일치하지 않게 입력 해주세요");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
}
