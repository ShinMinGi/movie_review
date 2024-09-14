package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(UserDto userDto);
    UserDto findByUsername(String emailId);
}
