package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.LessonDTO;
import ra.java_service_project.model.dto.response.LessonPreview;
import ra.java_service_project.model.entity.Lesson;
import ra.java_service_project.repository.CourseRepository;
import ra.java_service_project.repository.LessonRepository;
import ra.java_service_project.service.LessonService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public LessonDTO findById(Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
        return LessonDTO.builder()
                .lessonId(lesson.getLessonId())
                .title(lesson.getTitle())
                .contentUrl(lesson.getContentUrl())
                .textContent(lesson.getTextContent())
                .orderIndex(lesson.getOrderIndex())
                .isPublished(lesson.getIsPublished())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .courseId(lesson.getCourse() != null ? lesson.getCourse().getCourseId() : null)
                .build();
    }

    @Override
    public LessonDTO createLesson(LessonDTO lessonDTO, Integer courseId) {
        Lesson lesson = Lesson.builder()
                .title(lessonDTO.getTitle())
                .contentUrl(lessonDTO.getContentUrl())
                .textContent(lessonDTO.getTextContent())
                .orderIndex(lessonDTO.getOrderIndex())
                .isPublished(lessonDTO.getIsPublished())
                .createdAt(lessonDTO.getCreatedAt())
                .updatedAt(lessonDTO.getUpdatedAt())
                .course(courseRepository.findById(courseId).orElseThrow(() -> new NoSuchElementException("Course not found")))
                .build();
        Lesson lessonEntity = Lesson.builder()
                .lessonId(lesson.getLessonId())
                .title(lesson.getTitle())
                .contentUrl(lesson.getContentUrl())
                .textContent(lesson.getTextContent())
                .orderIndex(lesson.getOrderIndex())
                .isPublished(lesson.getIsPublished())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .course(lesson.getCourse())
                .build();

        Lesson savedLesson = lessonRepository.save(lessonEntity);

        return LessonDTO.builder()
                .lessonId(savedLesson.getLessonId())
                .title(savedLesson.getTitle())
                .contentUrl(savedLesson.getContentUrl())
                .textContent(savedLesson.getTextContent())
                .orderIndex(savedLesson.getOrderIndex())
                .isPublished(savedLesson.getIsPublished())
                .createdAt(savedLesson.getCreatedAt())
                .updatedAt(savedLesson.getUpdatedAt())
                .courseId(savedLesson.getCourse() != null ? savedLesson.getCourse().getCourseId() : null)
                .build();
    }

    @Override
    public LessonDTO updateLesson(LessonDTO lessonDTO, Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContentUrl(lessonDTO.getContentUrl());
        lesson.setTextContent(lessonDTO.getTextContent());
        lesson.setOrderIndex(lessonDTO.getOrderIndex());
        lesson.setIsPublished(lessonDTO.getIsPublished());
        lesson.setUpdatedAt(lessonDTO.getUpdatedAt());
        lesson.setUpdatedAt(LocalDateTime.now());
        Lesson updatedLesson = lessonRepository.save(lesson);
        return LessonDTO.builder()
                .lessonId(updatedLesson.getLessonId())
                .title(updatedLesson.getTitle())
                .contentUrl(updatedLesson.getContentUrl())
                .textContent(updatedLesson.getTextContent())
                .orderIndex(updatedLesson.getOrderIndex())
                .isPublished(updatedLesson.getIsPublished())
                .createdAt(updatedLesson.getCreatedAt())
                .updatedAt(updatedLesson.getUpdatedAt())
                .courseId(updatedLesson.getCourse() != null ? updatedLesson.getCourse().getCourseId() : null)
                .build();
    }

    @Override
    public Boolean updateIsPublished(Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
        lesson.setIsPublished(!lesson.getIsPublished());
        lessonRepository.save(lesson);
        return lesson.getIsPublished();
    }

    @Override
    public Boolean deleteLesson(Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
        lessonRepository.delete(lesson);
        return true;
    }

    @Override
    public LessonPreview getContentPreview(Integer id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
        return LessonPreview.builder()
                .contentUrl(lesson.getContentUrl())
                .textContent(lesson.getTextContent())
                .build();
    }


}
