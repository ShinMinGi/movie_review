package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.ReviewBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

}
