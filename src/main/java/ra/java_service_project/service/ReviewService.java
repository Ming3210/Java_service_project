package ra.java_service_project.service;

import ra.java_service_project.model.dto.request.ReviewDTO;
import ra.java_service_project.model.entity.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByCourseId(Integer courseId);

    ReviewDTO createReview(ReviewDTO review, Integer courseId);

    ReviewDTO updateReview(ReviewDTO review, Integer reviewId);

    void deleteReview(Integer reviewId);
}
