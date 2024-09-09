package com.movieboard.movie_review.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Integer id;
    private String title;
    private String body;
    private String writer;
    private LocalDateTime inserted;
}
