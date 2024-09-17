package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;  // 비밀번호 암호화를 위한 PasswordEncoder


    // 회원가입 처리 메소드
    public void registerUser(UserDto userDto) {
        // 비밀번호를 암호화한 후 저장
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.insertUser(userDto); // DB에 사용자 정보를 저장합니다.
    }

}
