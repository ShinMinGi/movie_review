package com.movie_board.movie_review.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.repository.UserMapper;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername: " + username);

        // UserMapper를 사용하여 데이터베이스에서 사용자 정보를 조회합니다.
        UserDto userDto = userMapper.findByUsername(username);

        if (userDto == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // UserDetails 객체를 생성하여 반환합니다.
        return User.builder()
                .username(userDto.getUserName())
                .password(userDto.getPassword()) // 데이터베이스에서 가져온 암호화된 패스워드 사용
                .authorities("ROLE_USER") // 권한 설정
                .build();
    }
}
