package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.dto.response.StudentProgressResponse;
import ra.java_service_project.model.dto.response.TeacherCourseStats;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.service.CourseService;
import ra.java_service_project.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/reports")
public class ReportController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @GetMapping("top-courses")
    public ResponseEntity<APIDataResponse<List<CourseResponse>>> getTopCourses() {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.findCoursesOrderByEnrollmentsDesc(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("student-progress/{id}")
    public ResponseEntity<APIDataResponse<List<StudentProgressResponse>>> getStudentProgress(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", userService.getStudentProgress(id), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("teacher_courses_overview/{id}")
    public ResponseEntity<APIDataResponse<TeacherCourseStats>> getTeacherCoursesOverview(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", courseService.getTeacherStats(id), HttpStatus.OK), HttpStatus.OK);
    }
}
