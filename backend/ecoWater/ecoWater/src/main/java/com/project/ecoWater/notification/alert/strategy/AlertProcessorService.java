package com.project.ecoWater.notification.alert.strategy;

import com.project.ecoWater.notification.FirebaseNotificationService;
import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.notification.alert.UserAlertSettings;
import com.project.ecoWater.notification.alert.UserAlertSettingsRepository;
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

        List<UserAlertSettings> settingsList = alertSettingsRepository
                .findAllByUserAndAlertType(email, AlertType.valueOf(type.name()));

        for (UserAlertSettings settings : settingsList) {
            strategy.updateAlertState(settings, value);
            alertSettingsRepository.save(settings);
            if (strategy.shouldSendAlert(settings, value)) {
                notificationService.sendNotification(
                        email,
                        strategy.getTitle(),
                        strategy.getMessage(value),
                        strategy.getType()
                );
            }
        }

    }
}
