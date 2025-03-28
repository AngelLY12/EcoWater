package com.project.ecoWater.level.infraestrucutre;


import com.project.ecoWater.level.app.WaterTankLevelAppService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.app.UserApplicationService;
import com.project.ecoWater.user.domain.User;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/addLevel")
    public ResponseEntity<WaterTankLevel> save(@RequestBody WaterTankLevel waterTankLevel,
                                               @AuthenticationPrincipal UserDetails userDetails
    ) {
      try {
          WaterTankLevel result = waterTankLevelAppService.save(waterTankLevel, userDetails.getUsername());
          return ResponseEntity.ok(result);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }
    }

    @GetMapping("/getLevels")
    public ResponseEntity<List<WaterTankLevel>> getAll() {
        try {
            List<WaterTankLevel> result = waterTankLevelAppService.findAll();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getLevel/{id}")
    public ResponseEntity<WaterTankLevel> getById(@PathVariable Long id) {
        try {
            WaterTankLevel level= waterTankLevelAppService.findById(id);
            return ResponseEntity.ok(level);
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
