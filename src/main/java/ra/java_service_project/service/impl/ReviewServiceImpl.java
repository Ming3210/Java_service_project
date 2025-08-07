package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.ReviewDTO;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.Review;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.CourseRepository;
import ra.java_service_project.repository.EnrollmentRepository;
import ra.java_service_project.repository.ReviewRepository;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<ReviewDTO> getReviewsByCourseId(Integer courseId) {
        List<Review> reviews = reviewRepository.findByCourse_CourseId(courseId);
        return reviews.stream()
                .map(review -> ReviewDTO.builder()
                        .courseId(review.getCourse().getCourseId())
                        .userId(review.getStudent().getUserId())
                        .rating(review.getRating())
                        .comment(review.getComment())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO, Integer courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        //  Check nếu chưa đăng ký thì không cho đánh giá
        boolean isEnrolled = enrollmentRepository.existsByStudentAndCourse(currentUser, course);
        if (!isEnrolled) {
            throw new IllegalStateException("Bạn chưa đăng ký khóa học này nên không thể đánh giá.");
        }

        boolean alreadyReviewed = reviewRepository.existsByStudentAndCourse(currentUser, course);
        if (alreadyReviewed) {
            throw new IllegalStateException("Bạn đã đánh giá khóa học này rồi.");
        }

        Review newReview = Review.builder()
                .student(currentUser)
                .course(course)
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(newReview);

        return ReviewDTO.builder()
                .courseId(savedReview.getCourse().getCourseId())
                .userId(savedReview.getStudent().getUserId())
                .rating(savedReview.getRating())
                .comment(savedReview.getComment())
                .createdAt(savedReview.getCreatedAt())
                .updatedAt(savedReview.getUpdatedAt())
                .build();
    }


    @Override
    public ReviewDTO updateReview(ReviewDTO review, Integer courseId) {
        Review existingReview = reviewRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Review not found"));

        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setUpdatedAt(LocalDateTime.now());

        Review updatedReview = reviewRepository.save(existingReview);
        return ReviewDTO.builder()
                .courseId(updatedReview.getCourse().getCourseId())
                .userId(updatedReview.getStudent().getUserId())
                .rating(updatedReview.getRating())
                .comment(updatedReview.getComment())
                .createdAt(updatedReview.getCreatedAt())
                .updatedAt(updatedReview.getUpdatedAt())
                .build();

    }

    @Override
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("Review not found"));
        reviewRepository.delete(review);
    }

}
