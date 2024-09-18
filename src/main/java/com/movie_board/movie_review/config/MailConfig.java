package com.movie_board.movie_review.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setPort(465); // SSL 포트

        javaMailSender.setUsername("mingi0418@naver.com");
        javaMailSender.setPassword("alsrl0418"); // 실제 비밀번호 또는 앱 비밀번호

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties()
    {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("" +
                "mail.smtp.ssl.enable", "true"); // SSL 사용 시
        properties.setProperty("mail.smtp.ssl.trust", "smtp.naver.com"); // 신뢰할 호스트 설정
        properties.setProperty("mail.debug", "true"); // 디버깅 정보 출력

        return properties;
    }

}