package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;

public interface AlertStrategy {
    AlertType getType();
    boolean shouldSendAlert(UserAlertSettings settings, float value);
    String getTitle();
    String getMessage(float value);
}
