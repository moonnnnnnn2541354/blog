package com.sparta.blog.global.email;

import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;
    private int authNumber;

    public void createCode() {
        // 인증번호 생성하기
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += Integer.toString(random.nextInt(10));
        }
        authNumber = Integer.parseInt(code);
    }

    public void sendEmail(String toEmail,String title, String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail,title,text);
        try{
            javaMailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                "title: {}, text: {}",toEmail,title,text);
            throw new RuntimeException("런타임 에러");
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }


    public String joinEmail(String email) {
        createCode();
        String setFrom = "ioae7979@gmail.com";
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
            "회원 가입 이메일 인증을 완료 해주세요." + 	//html 형식으로 작성 !
                "<br><br>" +
                "인증 번호는 " + authNumber + "입니다.";
        mailSend(setFrom,toMail,title,content);
        return Integer.toString(authNumber);
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //이메일 메시지와 관련된 설정을 수행합니다.
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정 (운영진 이메일)
            helper.setTo(toMail);//이메일의 수신자 주소 설정 (회원가입 하는 사람 이메일)
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // 이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
    }
}
