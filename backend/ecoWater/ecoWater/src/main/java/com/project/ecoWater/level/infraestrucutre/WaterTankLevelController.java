package com.project.ecoWater.level.infraestrucutre;


import com.project.ecoWater.level.app.WaterTankLevelAppService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.notification.alert.MonitoringService;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.app.UserApplicationService;
import com.project.ecoWater.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/level")
@RequiredArgsConstructor
public class WaterTankLevelController {
    private final WaterTankLevelAppService waterTankLevelAppService;
    private final MonitoringService monitoringService;

    @PostMapping("/addLevel")
    public ResponseEntity<WaterTankLevel> save(@RequestBody WaterTankLevel waterTankLevel,
                                               @AuthenticationPrincipal UserDetails userDetails
    ) {
        WaterTankLevel result = waterTankLevelAppService.save(waterTankLevel, userDetails.getUsername());
        monitoringService.checkTankLevel(userDetails.getUsername(), waterTankLevel.getFillPercentage());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getLevels")
    public ResponseEntity<List<WaterTankLevel>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        List<WaterTankLevel> result = waterTankLevelAppService.findAll(userDetails.getUsername());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getLevel")
    public ResponseEntity<WaterTankLevel> getByEmail(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<WaterTankLevel> level= waterTankLevelAppService.findByEmail(userDetails.getUsername());
        return level.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
