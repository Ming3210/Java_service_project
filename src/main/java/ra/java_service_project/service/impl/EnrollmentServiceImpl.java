package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.EnrollmentDTO;
import ra.java_service_project.model.entity.Course;
import ra.java_service_project.model.entity.Enrollment;
import ra.java_service_project.model.entity.LessonProgress;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.CourseRepository;
import ra.java_service_project.repository.EnrollmentRepository;
import ra.java_service_project.repository.LessonProgressRepository;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.service.EnrollmentService;
import ra.java_service_project.utils.EnrollmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonProgressRepository lessonProgressRepository;

    @Override
    public List<Enrollment> getAllEnrollments() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return enrollmentRepository.findEnrollmentsByStudent_Username(username);
    }

    @Override
    public Enrollment registerEnrollment(EnrollmentDTO enrollmentRequest) {
        // 1. Lấy user đang đăng nhập
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // 2. Lấy course từ courseId (tránh null Course object)
        Course course = courseRepository.findById(enrollmentRequest.getCourseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found"));

        // Debug xem course có lấy ra không
        System.out.println("Course found: " + course.getTitle());

        // 3. Check đã enroll chưa
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Already enrolled in this course");
        }

        // 4. Tạo enrollment mới
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setProgressPercentage(BigDecimal.ZERO);

        return enrollmentRepository.save(enrollment);


    }

    @Override
    public Enrollment getEnrollmentById(Integer id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Enrollment not found"));
    }

    @Override
    public Boolean updateLessonProgress(Integer enrollmentId, Integer lessonId) {
        LessonProgress lessonProgress = lessonProgressRepository
                .findLessonProgressByEnrollment_EnrollmentIdAndLesson_LessonId(enrollmentId, lessonId);

        if (lessonProgress == null) {
            throw new NoSuchElementException("Lesson progress not found for this enrollment and lesson");
        }

        lessonProgress.setIsCompleted(true);
        lessonProgress.setCompletedAt(LocalDateTime.now());
        lessonProgressRepository.save(lessonProgress);

        Enrollment enrollment = lessonProgress.getEnrollment();
        long totalLessons = enrollment.getCourse().getLessonsList().size();
        long completedLessons = enrollment.getLessonProgresses().stream()
                .filter(LessonProgress::getIsCompleted)
                .count();

        BigDecimal newProgress = BigDecimal.valueOf((completedLessons * 100.0) / totalLessons);
        enrollment.setProgressPercentage(newProgress);

        if (completedLessons == totalLessons) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            enrollment.setCompletionDate(LocalDateTime.now());
        }

        enrollmentRepository.save(enrollment);
        return lessonProgress.getIsCompleted();
    }



}
