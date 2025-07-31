package ra.java_service_project.service;

import org.springframework.data.domain.Page;
import ra.java_service_project.model.dto.request.CourseRequest;
import ra.java_service_project.model.dto.request.CourseUpdateRequest;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.entity.Course;

public interface CourseService {
    Page<CourseRequest> getAllCourses(Integer page, Integer itemPage, String sortBy, Boolean orderBy);

    CourseRequest getCourseById(Integer id);

    CourseResponse createCourse(CourseRequest course);

    Course deleteCourse(Integer id);

    CourseResponse updateCourse(CourseUpdateRequest course, Integer id);


}
