package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {


    private final ReviewBoardMapper reviewBoardMapper;


    // Read All
    public List<ReviewBoardDto> findAllReviews() {
        return reviewBoardMapper.findAllReviews();
    }
    //
    // Create
    public void createReview(ReviewBoardDto review) {
        reviewBoardMapper.createReview(review);
    }

    //SelectOne
    public ReviewBoardDto selectOne(Long id) {
        return reviewBoardMapper.findById(id);
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
    public List<ReviewBoardDto> getPagedList(int page, int pageSize, String searchKeyword, String filter) {
        int offset = (page - 1) * pageSize;  // OFFSET 계산
        return reviewBoardMapper.selectPagedList(offset, pageSize, searchKeyword, filter);  // 명시적으로 전달
    }

    // 전체 게시글 수 가져오기
    public int getTotalCount() {
        return reviewBoardMapper.selectTotalCount();
    }


    // 총 리뷰 수를 가져오는 메서드 추가 (-서칭)
    public int getTotalReviews(String searchKeyword, String filter) {
        return reviewBoardMapper.countReviews(searchKeyword,filter);
    }

    // 영화마다 해당 영화에대한 게시판 생성
    public List<ReviewBoardDto> getReviewsByMovieId(Long movieId) {
        return reviewBoardMapper.getReviewsByMovieId(movieId);
    }

}