package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.*;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.dto.response.CourseSearchResponse;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.Lesson;
import ra.java_service_project.model.entity.Review;
import ra.java_service_project.service.CourseService;
import ra.java_service_project.service.LessonService;
import ra.java_service_project.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping ("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;
    @Autowired
    private ReviewService reviewService;

//    @GetMapping
//    public ResponseEntity<APIDataResponse<Page<CourseRequest>>> getAllCourses(@RequestBody CoursePagingRequest coursePagingRequest) {
//        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.getAllCourses(coursePagingRequest.getPage(), coursePagingRequest.getItemPage(), coursePagingRequest.getSortBy(), coursePagingRequest.getOrderBy()), HttpStatus.OK), HttpStatus.OK);
//    }

    @GetMapping("{id}")
    public ResponseEntity<APIDataResponse<CourseRequest>> getCourseById(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.getCourseById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<CourseResponse>> createCourse(@RequestBody CourseRequest courseRequest) {
        CourseResponse response = courseService.createCourse(courseRequest);
        return ResponseEntity.ok(new APIDataResponse<>(true, "success", response, HttpStatus.OK));
    }



    @PutMapping("{id}")
    public ResponseEntity<APIDataResponse<CourseResponse>> updateCourse(@RequestBody CourseUpdateRequest course, @PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success update", courseService.updateCourse(course, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<APIDataResponse<Course>> deleteCourse(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success delete", courseService.deleteCourse(id), HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/lessons")
    public ResponseEntity<APIDataResponse<List<Lesson>>> getLessonsByCourseId(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.getLessonsByCourseId(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("{id}/lessons")
    public ResponseEntity<APIDataResponse<LessonDTO>> createLesson(@RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.createLesson(lessonDTO, lessonDTO.getCourseId()), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIDataResponse<List<CourseSearchResponse>>> search(@RequestParam(name = "search") String keyword) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.searchCourses(keyword), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("teacher-id/{id}")
    public ResponseEntity<APIDataResponse<List<CourseResponse>>> getCoursesByTeacherId(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.filterCoursesByUserId(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("{id}/reviews")
    public ResponseEntity<APIDataResponse<ReviewDTO>> createReview(@RequestBody ReviewDTO reviewRequest, @PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", reviewService.createReview(reviewRequest, id), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<APIDataResponse<List<CourseResponse>>> getReviewsByStatus(@RequestParam String status) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.filterCoursesByStatus(status), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("{id}/reviews")
    public ResponseEntity<APIDataResponse<List<ReviewDTO>>> getReviewById(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", reviewService.getReviewsByCourseId(id), HttpStatus.OK), HttpStatus.OK);
    }

}

