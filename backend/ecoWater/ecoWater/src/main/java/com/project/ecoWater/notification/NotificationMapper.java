package com.project.ecoWater.notification;

import com.project.ecoWater.notification.alert.AlertDTO;
import com.project.ecoWater.notification.alert.UserAlertSettings;

public class NotificationMapper {
    public static NotificationDTO mapToDto(NotificationEntity notification) {
        return NotificationDTO.builder()
                .idNotification(notification.getIdNotification())
                .title(notification.getTitle())
                .alertType(notification.getAlertType())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
