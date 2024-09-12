package com.movie_board.movie_review;

import com.movie_board.movie_review.repository.ReviewBoardMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/resources/mapper/ReviewBoardMapper.xml")
public class test {


}
