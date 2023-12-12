package com.sparta.blog.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$", message = "대소문자 또는 숫자를 사용하여, 3글자 이상 입력해주세요.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$", message = "대소문자 또는 숫자를 사용하여, 4글자 이상 입력해주세요.")
    private String password;
    private boolean admin = false;
    private String adminToken = "";

}
