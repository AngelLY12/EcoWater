package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.alert.AlertDTO;
import com.project.ecoWater.notification.alert.AlertMapper;
import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HighConsumptionAlertStrategy implements AlertStrategy {
    @Override
    public AlertType getType() {
        return AlertType.HIGH_CONSUMPTION;
    }

    @Override
    public boolean shouldSendAlert(UserAlertSettings settings, float value) {
        boolean enabled = settings.isEnabled();
        float threshold = settings.getThreshold();
        boolean wasSent = settings.isWasSent();

        boolean shouldSend = enabled && value > threshold && !wasSent;
        log.debug("🚦 [HighConsumption] Evaluando envío de alerta - enabled: {}, threshold: {}, wasSent: {}, value: {}, resultado: {}",
                enabled, threshold, wasSent, value, shouldSend);
        return shouldSend;
    }
    @Override
    public AlertDTO updateAlertState(UserAlertSettings settings, float value) {
        AlertDTO updatedSettings = AlertMapper.mapToDto(settings);
        if (value > updatedSettings.getThreshold() && !updatedSettings.isWasSent()) {
            updatedSettings.setWasSent(true);
            log.debug("🔄 [HighConsumption] Estado actualizado a wasSent=true para valor: {}", value);
        } else if (value <= updatedSettings.getThreshold() && updatedSettings.isWasSent()) {
            updatedSettings.setWasSent(false);
            log.debug("🔄 [HighConsumption] Estado actualizado a wasSent=false para valor: {}", value);

        }else{
            log.debug("🔄 [HighConsumption] Estado no modificado para valor: {}", value);

        }
        return updatedSettings;

    }
    @Override
    public String getTitle() {
        return "Consumo alto de agua";
    }

    @Override
    public String getMessage(float value) {
        return String.format("¡Alto consumo detectado! %.1fL", value);
    }
}
