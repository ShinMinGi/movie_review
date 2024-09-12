package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.ReviewBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewBoardService {

    @Autowired
    private ReviewBoardMapper reviewBoardMapper;


    // Read All
    public List<ReviewBoardDto> findAllReviews() {
        return reviewBoardMapper.findAllReviews();
    }
//
    // Create
    public void createReview(ReviewBoardDto review) {
        reviewBoardMapper.createReview(review);
    }
//    // Read By ID
//    public ReviewBoardDto getReviewById(Long id) {
//        return reviewBoardMapper.getReviewById(id);
//    }
//
//    // Update
//    public void updateReview(ReviewBoardDto review) {
//        reviewBoardMapper.updateReview(review);
//    }
//
//    // Delete
//    public void deleteReview(Long id) {
//        reviewBoardMapper.deleteReview(id);
//    }
}
