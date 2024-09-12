package com.movie_board.movie_review;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@MapperScan("com.movie_board.movie_review.repository")
@SpringBootApplication
public class MovieReviewApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieReviewApplication.class, args);
	}
}
