package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.ReviewDTO;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;



    @PutMapping("{id}")
    public ResponseEntity<APIDataResponse<ReviewDTO>> updateReview(@RequestBody ReviewDTO reviewRequest, @PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", reviewService.updateReview(reviewRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(
                new APIDataResponse<>(true, "success", null, HttpStatus.NO_CONTENT),
                HttpStatus.NO_CONTENT
        );
    }


}
