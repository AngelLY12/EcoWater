package com.project.ecoWater.notification.alert;

import com.project.ecoWater.user.infrastructure.UserMapper;

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
                .user(UserMapper.userEntityToUserDTO(alert.getUser()))
                .build();
    }
    public static UserAlertSettings mapToEntity(AlertDTO alert) {
        return UserAlertSettings.builder()
                .id(alert.getId())
                .alertType(alert.getAlertType())
                .enabled(alert.isEnabled())
                .threshold(alert.getThreshold())
                .unit(alert.getUnit())
                .createdAt(alert.getCreatedAt())
                .title(alert.getTitle())
                .message(alert.getMessage())
                .wasSent(alert.isWasSent())
                .user(UserMapper.userDTOToUserEntity(alert.getUser()))
                .build();
    }

}
