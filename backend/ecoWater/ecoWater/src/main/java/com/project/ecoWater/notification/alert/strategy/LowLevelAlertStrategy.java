package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import org.springframework.stereotype.Component;

@Component
public class LowLevelAlertStrategy implements AlertStrategy {
    @Override
    public AlertType getType() {
        return AlertType.LOW_LEVEL;
    }

    @Override
    public boolean shouldSendAlert(UserAlertSettings settings, float value) {
        return settings.isEnabled() && value <= settings.getThreshold();
    }

    @Override
    public String getTitle() {
        return "Nivel bajo de agua";
    }

    @Override
    public String getMessage(float value) {
        return String.format("El nivel está al %.1f%%", value);
    }
}
