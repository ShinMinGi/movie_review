package com.movieboard.movie_review.controller;

import com.movieboard.movie_review.dto.BoardDTO;
import com.movieboard.movie_review.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/mr")
    public String mvreview() {
        return "mv_review";
    }

    @PostMapping("/index")
    public String index(BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.mvreview(boardDTO);
        return "index";
    }

    @GetMapping("/i")
    public String index() {
        return "index";
    }

    @GetMapping("event")
    public String event() {
        return "event";
    }
    }
