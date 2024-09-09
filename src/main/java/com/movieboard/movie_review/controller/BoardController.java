package com.movieboard.movie_review.controller;


import com.movieboard.movie_review.domain.Review;
import com.movieboard.movie_review.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class BoardController {

    @Autowired
    private BoardMapper mapper;

    @GetMapping({"/","MovieReview"})
    public String mvreview() {
        return "mv_review";
    }


    @GetMapping("/movie/board")
    public String list(Model model) {
        List<Review> list = mapper.selectAll();
        model.addAttribute("reviewList", list);
        System.out.println(list.size());
        return "mv_review_board";
    }

    @GetMapping("event")
    public String event() {
        return "event";
    }


    @GetMapping("event2")
    public String event2() {
        return "event2";
    }

    @GetMapping("write")
    public String write() {
        return "write";
    }
}