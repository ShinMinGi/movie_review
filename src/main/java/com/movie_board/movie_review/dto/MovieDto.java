package com.movie_board.movie_review.dto;

import lombok.Data;

@Data
public class MovieDto {

    private int movieId;
    private String title;
    private String posterPath;
    private int releaseYear;
    private String country;

}
