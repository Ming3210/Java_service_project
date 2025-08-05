package ra.java_service_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.LessonDTO;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.LessonPreview;
import ra.java_service_project.model.entity.Lesson;
import ra.java_service_project.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("{id}")
    private ResponseEntity<APIDataResponse<LessonDTO>> getLessonById(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.findById(id), HttpStatus.OK), HttpStatus.OK);
    }


    @PutMapping("{id}")
    private ResponseEntity<APIDataResponse<LessonDTO>> updateLesson(@RequestBody LessonDTO lessonDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.updateLesson(lessonDTO, id), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("{id}/publish")
    private ResponseEntity<APIDataResponse<Boolean>> updateIsPublished(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.updateIsPublished(id), HttpStatus.OK), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    private ResponseEntity<APIDataResponse<Boolean>> deleteLesson(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.deleteLesson(id), HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/content-preview")
    private ResponseEntity<APIDataResponse<LessonPreview>> getContentPreview(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", lessonService.getContentPreview(id), HttpStatus.OK), HttpStatus.OK);
    }
}
