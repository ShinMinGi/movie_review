package com.movie_board.movie_review.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
@Configuration
public class MailConfig {

//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.naver.com");
//        mailSender.setPort(465);
//        mailSender.setUsername("mingi0418@naver.com");
//        mailSender.setPassword("alsrl0418");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//@Bean
//public JavaMailSender javaMailService() {
//
//    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//    javaMailSender.setHost("smtp.naver.com");
//
//    javaMailSender.setUsername("mingi0418");
//    javaMailSender.setPassword("alsrl0418");
//
//    javaMailSender.setPort(465);
//
//    javaMailSender.setJavaMailProperties(getMailProperties());
//
//    return javaMailSender;
//}
//
//    private Properties getMailProperties(){
//        Properties properties = new Properties();
//        properties.setProperty("mail.transport.protocol", "smtp"); // 프로토콜 설정
//        properties.setProperty("mail.smtp.auth", "true"); // smtp 인증
//        properties.setProperty("mail.smtp.starttls.enable", "true"); // smtp strattles 사용
//        properties.setProperty("mail.debug", "true"); // 디버그 사용
//        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com"); // ssl 인증 서버는 smtp.naver.com
//        properties.setProperty("mail.smtp.ssl.enable","true"); // ssl 사용
//        return properties;
//    }
//

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(465);
        mailSender.setUsername("mingi0418@naver.com");
        mailSender.setPassword("alsrl0418"); // 실제 비밀번호 또는 앱 비밀번호

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
