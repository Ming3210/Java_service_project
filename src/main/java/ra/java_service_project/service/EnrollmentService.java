package ra.java_service_project.service;

import ra.java_service_project.model.dto.request.EnrollmentDTO;
import ra.java_service_project.model.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {

    List<Enrollment> getAllEnrollments();

    Enrollment registerEnrollment(EnrollmentDTO enrollment);

    Enrollment getEnrollmentById(Integer id);

    Boolean updateLessonProgress(Integer enrollmentId, Integer lessonId);

}
