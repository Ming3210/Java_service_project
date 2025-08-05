package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.dto.request.NotificationRequest;
import ra.java_service_project.model.dto.response.NotificationResponse;
import ra.java_service_project.model.entity.Notification;
import ra.java_service_project.model.entity.User;
import ra.java_service_project.repository.NotificationRepository;
import ra.java_service_project.repository.UserRepository;
import ra.java_service_project.service.NotificationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NotificationResponse> getAllUserNotifications() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<Notification> notifications = notificationRepository.findByUser(currentUser);
        return notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .notificationId(notification.getNotificationId())
                        .userId(notification.getUser().getUserId())
                        .message(notification.getMessage())
                        .type(notification.getType())
                        .targetUrl(notification.getTargetUrl())
                        .isRead(notification.getIsRead())
                        .createdAt(notification.getCreatedAt())
                        .build()
                )
                .toList();
    }

    @Override
    public Boolean readNotification(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
        return true;
    }

    @Override
    public NotificationResponse createNotification(NotificationRequest notification, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Notification newNotification = Notification.builder()
                .user(user)
                .message(notification.getMessage())
                .type(notification.getType())
                .targetUrl(notification.getTargetUrl())
                .build();
        notificationRepository.save(newNotification);
        return NotificationResponse.builder()
                .notificationId(newNotification.getNotificationId())
                .userId(newNotification.getUser().getUserId())
                .message(newNotification.getMessage())
                .type(newNotification.getType())
                .targetUrl(newNotification.getTargetUrl())
                .isRead(newNotification.getIsRead())
                .createdAt(newNotification.getCreatedAt())
                .build();

    }

    @Override
    public Boolean deleteNotification(Integer notificationId) {
        try {
            Notification notification = notificationRepository.findById(notificationId)
                    .orElseThrow(() -> new NoSuchElementException("Notification not found"));

            notificationRepository.delete(notification);
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }
}
