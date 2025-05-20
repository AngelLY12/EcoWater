package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import org.springframework.stereotype.Component;

@Component
public class HighConsumptionAlertStrategy implements AlertStrategy {
    @Override
    public AlertType getType() {
        return AlertType.HIGH_CONSUMPTION;
    }

    @Override
    public boolean shouldSendAlert(UserAlertSettings settings, float value) {
        return settings.isEnabled() && value > settings.getThreshold() && !settings.isWasSent();
    }
    @Override
    public void updateAlertState(UserAlertSettings settings, float value) {
        if (value > settings.getThreshold() && !settings.isWasSent()) {
            settings.setWasSent(true);
        } else if (value <= settings.getThreshold() && settings.isWasSent()) {
            settings.setWasSent(false);
        }

    }
    @Override
    public String getTitle() {
        return "Consumo alto de agua";
    }

    @Override
    public String getMessage(float value) {
        return String.format("¡Alto consumo detectado! %.1f%%", value);
    }
}
