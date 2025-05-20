package com.project.ecoWater.notification.alert;

public class AlertMapper {
    public static AlertDTO mapToDto(UserAlertSettings alert) {
        return AlertDTO.builder()
                .id(alert.getId())
                .alertType(alert.getAlertType())
                .enabled(alert.isEnabled())
                .threshold(alert.getThreshold())
                .unit(alert.getUnit())
                .createdAt(alert.getCreatedAt())
                .title(alert.getTitle())
                .message(alert.getMessage())
                .wasSent(alert.isWasSent())
                .build();
    }

}
