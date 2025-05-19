package com.project.ecoWater.notification;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.user.infrastructure.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long idNotification;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private AlertType alertType;
}
