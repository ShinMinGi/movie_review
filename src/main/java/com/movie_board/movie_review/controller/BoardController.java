package com.movie_board.movie_review.controller;


import com.movie_board.movie_review.dto.MovieDto;
import com.movie_board.movie_review.dto.PageDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.service.ReviewBoardService;
import com.movie_board.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private ReviewBoardService reviewBoardService;


    @GetMapping("/")
    public String mvreview(Model model) {
        // 영화 리스트 가져오기
        List<MovieDto> movies = reviewBoardService.getAllMovies();

        model.addAttribute("movies", movies);
        return "mv_review";
    }

    // 영화별 리뷰 게시판으로 이동
    @GetMapping("/movie/review/{movieId}")
    public String getMovieReviews(
            @PathVariable int movieId,
            Model model) {

        // 해당 영화에 대한 리뷰 리스트 가져오기
        List<ReviewBoardDto> reviews = reviewBoardService.getReviewsByMovieId(movieId);

        // 해당 영화 정보 가져오기
        MovieDto movie = reviewBoardService.getMovieById(movieId);

        // 모델에 리뷰와 영화 정보 추가
        model.addAttribute("reviewList", reviews);
        model.addAttribute("movie", movie);

        return "mv_review_board"; // 동적 리뷰 게시판 템플릿
    }

    // Read All (GET 요청으로 모든 리뷰 조회)  ,  페이징 기능도 동시에 구현

    @GetMapping("/movie/board/{movieId}")
    public String getAllReviews(
            @PathVariable int movieId, //movie를 PathVariable로 받아옴
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            Model model) {

        int totalReviews = reviewBoardService.getTotalReviews(movieId, searchKeyword, filter);
        PageDto pageDto = new PageDto(totalReviews, page, pageSize, searchKeyword, filter);

        // 페이지에 맞는 리뷰 가져오기
        List<ReviewBoardDto> reviews = reviewBoardService.getPagedList(movieId, page, pageSize, searchKeyword, filter);

        // 모델에 추가
        model.addAttribute("reviewList", reviews);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("movieId", movieId);

        return "mv_review_board";  // mv_review_board.html 파일로 연결
    }



    // 글쓰기 등록/삭제/수정 페이지 화면
    // 글쓰기 페이지 화면 (영화별로 글을 쓸 수 있도록 movieId 필요)
    //    @PreAuthorize("hasRole('USER')")

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/movie/write/{movieId}")
    public String write(@PathVariable int movieId, Model model) {
        model.addAttribute("movieId", movieId);
        // 다른 필요한 모델 속성 추가
        return "write";
    }





    // 글쓰기 등록 구현
    // 글쓰기 등록 (movieId를 받아서 그 영화에 맞는 리뷰 작성)
    @PostMapping("/movie/board/{movieId}")
    public String createReview(
            @PathVariable int movieId,
            @RequestParam String writer,
            @RequestParam String title,
            @RequestParam String body) {
        ReviewBoardDto review = new ReviewBoardDto();
        review.setMovieId(movieId);
        review.setWriter(writer);
        review.setTitle(title);
        review.setBody(body);
        log.info("Creating review with movieId: {}, writer: {}, title: {}, body: {}", movieId, writer, title, body);

        reviewBoardService.createReview(review);
        // 폼 데이터를 처리하는 로직 (예: 데이터베이스에 저장)
        log.info("createReview={}", review);
        // 데이터를 처리한 후 다시 mv_review_board로 리다이렉트

        return "redirect:/movie/board/" + movieId;

    }



    // 게시글 상세 보기 Read
//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/movie/read/{movieId}/{id}")
    public String read(@PathVariable int movieId, @PathVariable Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id, movieId);
        model.addAttribute("review", review);
        model.addAttribute("movieId", movieId);
        return "mv_review_board_detail"; // 해당 HTML 파일을 렌더링
    }

    // 게시글 삭제
    @PostMapping("/movie/{movieId}/remove/{id}")
    public String remove(@PathVariable int movieId, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewBoardService.deleteReview(id);
        log.info("-------------------------remove------------------------------");
        log.info("id : " + id);
        return "redirect:/movie/board/" + movieId;
    }



    // 게시글 수정 edit
    @GetMapping("/edit/{movieId}/{id}")
    public String editReview(@PathVariable int movieId, @PathVariable Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id, movieId); // 리뷰 조회
        model.addAttribute("review", review); // 모델에 리뷰 추가
        model.addAttribute("movieId", movieId);
        return "edit"; // 수정 폼 템플릿
    }

    // 게시글 수정 처리 (movieId를 받아서 수정 후 다시 그 영화의 리뷰 페이지로 리다이렉트)
    @PostMapping("/{movieId}/edit/{id}")
    public String updateReview(@PathVariable int movieId, @PathVariable Long id, @ModelAttribute ReviewBoardDto reviewBoardDto) {
        reviewBoardService.editReview(reviewBoardDto);
        return "redirect:/movie/read/" + movieId + "/" + id;
    }


    @GetMapping("event")
    public String event() {
        return "event";
    }


    @GetMapping("event2")
    public String event2() {
        return "event2";
    }



    // login page
    @GetMapping("/login")
    public String loginGET(String errorCode, String logout) {
        log.info("login get..........................");
        log.info("logout: " + logout);

        if (logout != null) {
            log.info("user logout...................................");
        }
        return "login"; // login.html 파일을 반환
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }





}