package com.movie_board.movie_review.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String userName;
    private String emailId;
    private String password;
    private String redirectUrl;
    private LocalDateTime createdAt;
}
