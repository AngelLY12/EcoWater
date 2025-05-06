package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import org.springframework.stereotype.Component;

@Component
public class MediumLevelAlertStrategy implements AlertStrategy {
    @Override
    public AlertType getType() {
        return AlertType.MEDIUM_LEVEL;
    }

    @Override
    public boolean shouldSendAlert(UserAlertSettings settings, float value) {
        return settings.isEnabled() && value > settings.getThreshold() && value <= 60;
    }

    @Override
    public String getTitle() {
        return "Nivel medio de agua";
    }

    @Override
    public String getMessage(float value) {
        return String.format("Nivel actual: %.1f%%. Todo en orden.", value);
    }
}
