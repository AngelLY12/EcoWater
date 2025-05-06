package com.project.ecoWater.notification.alert;

import com.project.ecoWater.notification.FirebaseNotificationService;
import com.project.ecoWater.notification.alert.strategy.AlertProcessorService;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonitoringService {
    private final UserRepository userRepository;
    private final UserAlertSettingsRepository alertSettingsRepository;
    private final FirebaseNotificationService firebaseNotificationService;
    private final AlertProcessorService alertProcessorService;
    public void checkTankLevel(String email, float currentLevel) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<AlertType> levelAlerts = List.of(
                AlertType.LOW_LEVEL,
                AlertType.MEDIUM_LEVEL,
                AlertType.HIGH_LEVEL,
                AlertType.FULL_TANK
        );

        for (AlertType type : levelAlerts) {
            alertProcessorService.processAlert(user.getEmail(), type, currentLevel);
        }
    }
    public void checkWaterConsumption(String email, float consumption) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        alertProcessorService.processAlert(user.getEmail(), AlertType.HIGH_CONSUMPTION, consumption);
    }

}
