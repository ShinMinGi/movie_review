package com.movieboard.movie_review.mapper;

import com.movieboard.movie_review.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Select("""
            SELECT
                id,
                title,
                body,
                writer,
                inserted
            FROM Review
            ORDER BY id DESC
            """)
    List<Review> selectAll();
}
