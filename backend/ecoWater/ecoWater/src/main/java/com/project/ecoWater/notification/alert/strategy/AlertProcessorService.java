package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.FirebaseNotificationService;
import com.project.ecoWater.notification.alert.*;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertProcessorService {

    private final UserAlertSettingsRepository alertSettingsRepository;
    private final FirebaseNotificationService notificationService;
    private final Map<AlertType, AlertStrategy> strategyMap;

    public AlertProcessorService(List<AlertStrategy> strategies,
                                 UserAlertSettingsRepository alertSettingsRepository,
                                 FirebaseNotificationService notificationService) {
        this.alertSettingsRepository = alertSettingsRepository;
        this.notificationService = notificationService;
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(AlertStrategy::getType, s -> s));
    }

    public void processAlert(String email, AlertType type, float value) {
        AlertStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            log.warn("No strategy found for type: {}", type);
            return;
        }
        log.info("🔔 Procesando alerta de tipo '{}' con valor '{}' para usuario '{}'", type, value, email);

        List<UserAlertSettings> settingsList = alertSettingsRepository
                .findAllByUserAndAlertType(email, AlertType.valueOf(type.name()));
        log.debug("Configuraciones de alerta recuperadas para '{}': {}", email, settingsList);

        for (UserAlertSettings settings : settingsList) {
            log.debug("Evaluando configuración: {}", settings);
            boolean shouldSend = strategy.shouldSendAlert(settings, value);
            log.debug("¿Se debe enviar alerta? {}", shouldSend);
            if (shouldSend) {
                log.info("✅ Enviando notificación para '{}' con tipo '{}' y valor '{}'", email, type, value);

                notificationService.sendNotification(
                        email,
                        strategy.getTitle(),
                        strategy.getMessage(value),
                        strategy.getType()
                );
            }else{
                log.info("❌ No se envió notificación para '{}' porque no se cumple la condición del umbral", email);

            }
            AlertDTO updated =strategy.updateAlertState(settings, value);
            alertSettingsRepository.save(AlertMapper.mapToEntity(updated));
            log.debug("Estado de alerta actualizado y guardado: {}", updated);
        }
        log.info("🔁 Proceso de alerta completado para '{}'", email);


    }

}
