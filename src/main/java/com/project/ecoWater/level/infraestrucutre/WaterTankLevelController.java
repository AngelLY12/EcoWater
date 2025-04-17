package com.project.ecoWater.level.infraestrucutre;


import com.project.ecoWater.level.app.WaterTankLevelAppService;
import com.project.ecoWater.level.domain.WaterTankLevel;
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

    @PostMapping("/addLevel")
    public ResponseEntity<WaterTankLevel> save(@RequestBody WaterTankLevel waterTankLevel,
                                               @AuthenticationPrincipal UserDetails userDetails
    ) {
      try {
          WaterTankLevel result = waterTankLevelAppService.save(waterTankLevel, userDetails.getUsername());
          return ResponseEntity.ok(result);
      } catch (RuntimeException e) {
          throw new RuntimeException(e);
          //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

      }
    }

    @GetMapping("/getLevels")
    public ResponseEntity<List<WaterTankLevel>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            List<WaterTankLevel> result = waterTankLevelAppService.findAll(userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getLevel")
    public ResponseEntity<WaterTankLevel> getByEmail(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<WaterTankLevel> level= waterTankLevelAppService.findByEmail(userDetails.getUsername());
            return level.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.noContent().build());
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
