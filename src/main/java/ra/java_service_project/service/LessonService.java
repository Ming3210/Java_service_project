package ra.java_service_project.service;

import ra.java_service_project.model.dto.request.LessonDTO;
import ra.java_service_project.model.dto.response.LessonPreview;
import ra.java_service_project.model.entity.Lesson;

public interface LessonService {
    LessonDTO findById(Integer id);

    LessonDTO createLesson(LessonDTO lessonDTO, Integer courseId);

    LessonDTO updateLesson(LessonDTO lessonDTO, Integer id);

    Boolean updateIsPublished(Integer id);

    Boolean deleteLesson(Integer id);

    LessonPreview getContentPreview(Integer id);
}
