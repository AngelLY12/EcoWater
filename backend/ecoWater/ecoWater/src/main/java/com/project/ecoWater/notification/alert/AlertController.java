package com.project.ecoWater.notification.alert;

import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final UserAlertSettingsService userAlertSettingsService;
    private final UserRepository userRepository;
    private final UserAlertSettingsRepository userAlertSettingsRepository;




    @GetMapping("/getAllAlerts")
    public ResponseEntity<List<AlertDTO>> getUserAlerts(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<AlertDTO> dtos = userAlertSettingsService.getAllUserAlertSettings(userDetails.getUsername());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/addAlert")
    public ResponseEntity<UserAlertSettings> addAlert(@RequestBody UserAlertSettings userAlertSettings,@AuthenticationPrincipal UserDetails userDetails) {
        UserAlertSettings addUserAlert= userAlertSettingsService.addUserAlert(userDetails.getUsername(),userAlertSettings);
        return ResponseEntity.ok(addUserAlert);
    }
    @PatchMapping("/enable-status")
    public ResponseEntity<Boolean> enableStatus(@AuthenticationPrincipal UserDetails userDetails,@RequestBody UserAlertSettings userAlertSettings) {
        boolean updated= userAlertSettingsService.updateEnabledStatus(userDetails.getUsername(),userAlertSettings);
        return ResponseEntity.ok(updated);
    }

}