package com.movie_board.movie_review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {
    private String userName;
    private String emailId;
    private String password;
    private String redirectURL;
    private LocalDateTime createdAt;
}
