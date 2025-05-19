package com.project.ecoWater.notification;

import com.google.api.gax.paging.Page;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController()
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final FirebaseNotificationService firebaseNotificationService;

    @GetMapping("/listAll")
    public ResponseEntity<List<NotificationDTO>> getNotificaciones(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<NotificationDTO> notifications = firebaseNotificationService.getNotifications(userDetails.getUsername());

        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(
            @RequestBody NotificationEntity request) {

        firebaseNotificationService.sendNotification(
                request.getUser().getEmail(),
                request.getTitle(),
                request.getMessage(),
                request.getAlertType()
        );

        return ResponseEntity.ok("Notificación enviada");
    }




}
