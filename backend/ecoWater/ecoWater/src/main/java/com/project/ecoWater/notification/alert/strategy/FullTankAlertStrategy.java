package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertDTO;
import com.project.ecoWater.notification.alert.AlertMapper;
import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import org.springframework.stereotype.Component;

@Component
public class FullTankAlertStrategy implements AlertStrategy {
    @Override
    public AlertType getType() {
        return AlertType.FULL_TANK;
    }

    @Override
    public boolean shouldSendAlert(UserAlertSettings settings, float value) {
        return settings.isEnabled() && value == settings.getThreshold() && !settings.isWasSent();
    }

    @Override
    public AlertDTO updateAlertState(UserAlertSettings settings, float value) {
        AlertDTO updatedSettings = AlertMapper.mapToDto(settings);
        if (value == updatedSettings.getThreshold() && !updatedSettings.isWasSent()) {
            updatedSettings.setWasSent(true);
        } else if (value < updatedSettings.getThreshold() && updatedSettings.isWasSent()) {
            updatedSettings.setWasSent(false);
        }
        return updatedSettings;
    }

    @Override
    public String getTitle() {
        return "Tanque lleno";
    }

    @Override
    public String getMessage(float value) {
        return String.format("¡El tanque esta lleno! %.1f%%", value);
    }
}
