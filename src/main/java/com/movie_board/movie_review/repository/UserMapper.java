package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.FindEmailDto;
import com.movie_board.movie_review.dto.FindPasswordDto;
import com.movie_board.movie_review.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UserMapper {

    void insertUser(UserDto userDto);


    // 사용자 정보조회
    UserDto findByUsername(String username);

    void createUser(UserDto userDto);

    UserDto findByEmail(String emailId);

    // 이메일로 사용자 정보를 찾는 메서드
    FindPasswordDto findUserByEmail(@Param("email") String email);

    // 비밀번호를 업데이트 하는 메서드
    void updateUserPassword(@Param("email") String email, @Param("newPassword") String newPassword);
}