package com.sparta.blog.global.email;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController {
    private final MailService mailService;

    @PostMapping("/mailSend")
    public String mailSend(@Valid @RequestBody EmailRequestDto requestDto) {
        log.warn("이메일 인증 이메일 : " + requestDto.getEmail());
        return mailService.joinEmail(requestDto.getEmail());
    }

}
