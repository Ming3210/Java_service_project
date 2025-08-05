package ra.java_service_project.service;

import ra.java_service_project.model.dto.request.NotificationRequest;
import ra.java_service_project.model.dto.response.NotificationResponse;
import ra.java_service_project.model.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getAllUserNotifications();

    Boolean readNotification(Integer notificationId);

    NotificationResponse createNotification(NotificationRequest notification, Integer userId);

    Boolean deleteNotification(Integer notificationId);

}
