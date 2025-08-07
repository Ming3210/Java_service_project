package ra.java_service_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.java_service_project.model.dto.request.NotificationRequest;
import ra.java_service_project.model.dto.response.APIDataResponse;
import ra.java_service_project.model.dto.response.NotificationResponse;
import ra.java_service_project.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<APIDataResponse<List<NotificationResponse>>> getAllUserNotifications() {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", notificationService.getAllUserNotifications(), HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("{id}/read")
    public ResponseEntity<APIDataResponse<Boolean>> readNotification(@PathVariable Integer id) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", notificationService.readNotification(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIDataResponse<NotificationResponse>> createNotification(@Valid @RequestBody NotificationRequest notification) {
        return new ResponseEntity<>(new APIDataResponse<>(true, "success", notificationService.createNotification(notification), HttpStatus.OK), HttpStatus.OK);
    }
}
