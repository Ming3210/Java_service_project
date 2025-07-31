package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.CourseRequest;
import ra.java_service_project.model.dto.request.CourseUpdateRequest;
import ra.java_service_project.model.dto.response.CourseResponse;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.CourseRepository;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.service.CourseService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<CourseRequest> getAllCourses(Integer page, Integer itemPage, String sortBy, Boolean orderBy) {
        Pageable pageable = null;
        Sort sort = null;
        if(sortBy!=null && !sortBy.isEmpty()) {
            if(orderBy){
                sort = Sort.by(Sort.Direction.ASC,sortBy);
            }else{
                sort = Sort.by(Sort.Direction.DESC,sortBy);
            }
            pageable = PageRequest.of(page,itemPage,sort);
        }else{
            pageable = PageRequest.of(page,itemPage);
        }

        return courseRepository.findAll(pageable)
                .map(course -> CourseRequest.builder()
                        .courseId(course.getCourseId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .price(course.getPrice())
                        .durationHours(course.getDurationHours())
                        .status(course.getStatus())
                        .createdAt(course.getCreatedAt())
                        .updatedAt(course.getUpdatedAt())
                        .teacherId(course.getTeacher().getUserId())
                        .build());
    }

    @Override
    public CourseRequest getCourseById(Integer id) {
        return courseRepository.findById(id)
                .map(course -> CourseRequest.builder()
                        .courseId(course.getCourseId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .price(course.getPrice())
                        .durationHours(course.getDurationHours())
                        .status(course.getStatus())
                        .createdAt(course.getCreatedAt())
                        .updatedAt(course.getUpdatedAt())
                        .teacherId(course.getTeacher().getUserId())
                        .build())
                .orElseThrow(()-> new RuntimeException("Course not found"));
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .durationHours(request.getDurationHours())
                .status(request.getStatus())
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Course saved = courseRepository.save(course);

        return CourseResponse.builder()
                .courseId(saved.getCourseId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .durationHours(saved.getDurationHours())
                .status(saved.getStatus())
                .teacherId(saved.getTeacher().getUserId())
                .createdAt(saved.getCreatedAt())
                .build();
    }



    @Override
    public Course deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));
        courseRepository.delete(course);
        return course;
    }


    @Override
    public CourseResponse updateCourse(CourseUpdateRequest request, Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setDurationHours(request.getDurationHours());
        course.setStatus(request.getStatus());

        if (request.getTeacherId() != null) {
            User newTeacher = userRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new NoSuchElementException("Teacher not found"));
            course.setTeacher(newTeacher);
        }

        course.setUpdatedAt(LocalDateTime.now());
        Course savedCourse = courseRepository.save(course);

        return CourseResponse.builder()
                .courseId(savedCourse.getCourseId())
                .title(savedCourse.getTitle())
                .description(savedCourse.getDescription())
                .price(savedCourse.getPrice())
                .durationHours(savedCourse.getDurationHours())
                .status(savedCourse.getStatus())
                .createdAt(savedCourse.getCreatedAt())
                .updatedAt(savedCourse.getUpdatedAt())
                .teacherId(savedCourse.getTeacher().getUserId())
                .build();
    }





}
