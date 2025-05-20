package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;

public interface AlertStrategy {
    AlertType getType();
    boolean shouldSendAlert(UserAlertSettings settings, float value);
    void updateAlertState(UserAlertSettings settings, float value); // ← Nuevo

    String getTitle();
    String getMessage(float value);
}
