package com.project.ecoWater.notification.alert;

import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAlertSettingsService {
    private final UserRepository userRepository;
    private final UserAlertSettingsRepository alertSettingsRepository;


    public UserAlertSettings addUserAlert(String email, UserAlertSettings userAlertSettings) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userAlertSettings.setUser(UserMapper.toEntity(user));
        return alertSettingsRepository.save(userAlertSettings);
    }


    public boolean updateEnabledStatus(String email, UserAlertSettings userAlertSettings) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<UserAlertSettings> optionalSettings =
                alertSettingsRepository.findByEmailAndId(email, userAlertSettings.getId());

        if (!optionalSettings.isPresent()) {
            throw new IllegalArgumentException("Alert with ID" + userAlertSettings.getId() + " not found for this user");
        }
        UserAlertSettings existingSettings = optionalSettings.get();
        existingSettings.setEnabled(userAlertSettings.isEnabled());

        alertSettingsRepository.save(existingSettings);
        return true;
    }

    public List<AlertDTO> getAllUserAlertSettings(String email) {
        List<UserAlertSettings> allAlerts = alertSettingsRepository.findByUser(email);
        List<AlertDTO> dtos = allAlerts.stream()
                .map(AlertMapper::mapToDto)
                .collect(Collectors.toList());
        if(dtos.isEmpty()){
            throw new IllegalArgumentException("No Alerts found for this user.");
        }
        return dtos;
    }

}
