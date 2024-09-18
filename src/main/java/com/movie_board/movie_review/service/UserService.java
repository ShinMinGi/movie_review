package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.FindPasswordDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 PasswordEncoder

    private final JavaMailSender javaMailSender;

    // 회원가입 처리 메소드
    public void registerUser(UserDto userDto) {
        // 비밀번호를 암호화한 후 저장
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.insertUser(userDto); // DB에 사용자 정보를 저장합니다.
    }

    // 이메일로 사용자를 찾는 메서드
    public FindPasswordDto findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    // 임시 비밀번호 생성 메서드
    private String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder tempPassword = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for(int i=0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            tempPassword.append(characters.charAt(index));
        }
        return tempPassword.toString();
    }

    // 사용자의 비밀번호를 임시 비밀번호로 업데이트하고 이메일을 전송하는 메서드
    public void resetUserPassword(String email) {
        // 사용자 정보 찾기
        FindPasswordDto findPw = userMapper.findUserByEmail(email);

        if (findPw != null) {
            // 임시 비밀번호 생성
            String temporaryPassword = generateTemporaryPassword();

            // 비밀번호 업데이트
            userMapper.updateUserPassword(email, temporaryPassword);

            // 이메일 전송
            sendTemporaryPasswordEmail(email, temporaryPassword);
        } else {
            throw new IllegalArgumentException("해당 이메일을 가진 사용자가 없습니다");
        }
    }


    // 임시 비밀번호를 이메일로 전송하는 메서드
    private void sendTemporaryPasswordEmail(String email, String temporaryPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("mingi0418@naver.com"); // 발신자 주소
        mailMessage.setTo(email);
        mailMessage.setSubject("임시 비밀번호 발급 안내");
        mailMessage.setText("안녕하세요,\n\n임시 비밀번호는 다음과 같습니다 : " + temporaryPassword + "\n\n로그인 후 비밀번호를 변경해 주세요");

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
    }
}
