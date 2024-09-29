package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.MovieDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {


    private final ReviewBoardMapper reviewBoardMapper;


    // Read All
    public List<ReviewBoardDto> findAllReviews() {
        return reviewBoardMapper.findAllReviews();
    }


    // Create
    public void createReview(ReviewBoardDto review) {
        reviewBoardMapper.createReview(review);
    }

    //SelectOne
    public ReviewBoardDto selectOne(Long id, int movieId) {
        return reviewBoardMapper.findById(id, movieId);
    }

    // Delete
    public void deleteReview(Long id) {
        reviewBoardMapper.deleteReview(id);
    }

    // update
    public int editReview(ReviewBoardDto reviewBoardDto) {
        return reviewBoardMapper.updateReview(reviewBoardDto);
    }

    // 총 게시글 수 가져오기,체 게시글 수를 계산하는 메서드 , 서칭,필터링 동시구현
    public List<ReviewBoardDto> getPagedList(int movieId, int page, int pageSize, String searchKeyword, String filter) {
        int offset = (page - 1) * pageSize;  // OFFSET 계산
        return reviewBoardMapper.selectPagedList(movieId, offset, pageSize, searchKeyword, filter);  // 명시적으로 전달
    }

    // 전체 게시글 수 가져오기
    public int getTotalCount() {
        return reviewBoardMapper.selectTotalCount();
    }


    // 총 리뷰 수를 가져오는 메서드 추가 (-서칭)
    public int getTotalReviews(int movieId, String searchKeyword, String filter) {
        return reviewBoardMapper.countReviews(movieId, searchKeyword,filter);
    }




    // 특정 영화에 대한 리뷰 리스트 가져오기 (동적 게시판구현)
    public List<ReviewBoardDto> getReviewsByMovieId(int movieId) {
        return reviewBoardMapper.getReviewsByMovieId(movieId);
    }

    // 특정 영화 정보 가져오기
    public MovieDto getMovieById(int movieId) {
        return reviewBoardMapper.getMovieById(movieId);
    }

    // 모든 영화 리스트 가져오기
    public List<MovieDto> getAllMovies() {
        return reviewBoardMapper.selectAllMovies();
    }

}