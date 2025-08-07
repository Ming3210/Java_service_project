package ra.java_service_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.EnrollmentDTO;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.entity.Enrollment;
import ra.java_service_project.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentConroller {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<APIDataResponse<List<Enrollment>>> getAllEnrollments() {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", enrollmentService.getAllEnrollments(), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<Enrollment>> registerEnrollment(@Valid @RequestBody EnrollmentDTO enrollment) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", enrollmentService.registerEnrollment(enrollment), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<APIDataResponse<Enrollment>> getEnrollmentById(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", enrollmentService.getEnrollmentById(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("{enrollmentId}/complete-lesson/{lessonId}")
    public ResponseEntity<APIDataResponse<Boolean>> updateLessonProgress(@PathVariable Integer enrollmentId, @PathVariable Integer lessonId) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", enrollmentService.updateLessonProgress(enrollmentId, lessonId), HttpStatus.OK), HttpStatus.OK);
    }


}
