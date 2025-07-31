package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.CoursePagingRequest;
import ra.java_service_project.model.dto.request.CourseRequest;
import ra.java_service_project.model.dto.request.CourseUpdateRequest;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.service.CourseService;

@RestController
@RequestMapping ("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<APIDataResponse<Page<CourseRequest>>> getAllCourses(@RequestBody CoursePagingRequest coursePagingRequest) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.getAllCourses(coursePagingRequest.getPage(), coursePagingRequest.getItemPage(), coursePagingRequest.getSortBy(), coursePagingRequest.getOrderBy()), HttpStatus.OK), HttpStatus.OK);
    }

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
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.updateCourse(course, id), HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<APIDataResponse<Course>> deleteCourse(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.deleteCourse(id), HttpStatus.OK), HttpStatus.OK);
    }
}
