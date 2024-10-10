package com.movie_board.movie_review.security;

import com.movie_board.movie_review.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetails {

    private final UserMapper userMapper;

}
