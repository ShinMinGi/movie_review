package com.movieboard.movie_review.repository;

import com.movieboard.movie_review.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final SqlSessionTemplate sql;
    public void mvreview(BoardDTO boardDTO) {
    }
}
