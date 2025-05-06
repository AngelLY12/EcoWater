package com.project.ecoWater.notification.alert;

public interface AlertStrategy {
    AlertType getType();
    boolean shouldSendAlert(UserAlertSettings settings, float value);
    String getTitle();
    String getMessage(float value);
}
