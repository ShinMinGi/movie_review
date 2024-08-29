package com.movieboard.movie_review.service;

import com.movieboard.movie_review.dto.BoardDTO;
import com.movieboard.movie_review.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    public void mvreview(BoardDTO boardDTO) {
        boardRepository.mvreview(boardDTO);
    }
}
