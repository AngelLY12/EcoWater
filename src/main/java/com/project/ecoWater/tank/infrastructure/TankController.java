package com.project.ecoWater.tank.infrastructure;


import com.project.ecoWater.tank.app.TankApplicationService;
import com.project.ecoWater.tank.domain.Tank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tank")
@RequiredArgsConstructor
public class TankController {

    private final TankApplicationService tankApplicationService;

    @PostMapping("/addTank")
    public ResponseEntity<Tank> createTank(@RequestBody Tank tank, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Tank tankSaved = tankApplicationService.saveTank(tank, userDetails.getUsername());
            return ResponseEntity.ok(tankSaved);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getTank/{tankId}")
    public ResponseEntity<Tank> getTankById(@PathVariable Long tankId) {
        try {
            Tank getTank=tankApplicationService.findTankById(tankId);
            return ResponseEntity.ok(getTank);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllTanks")
    public ResponseEntity<List<Tank>> getAllTanks() {
        try {
            List<Tank> getAllTanks=tankApplicationService.findAllTanks();
            return ResponseEntity.ok(getAllTanks);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateTank")
    public ResponseEntity<Optional<Tank>> updateTank(@RequestBody Tank tank) {
        try {
            Optional<Tank> updatedTank=tankApplicationService.updateTank(tank);
            return ResponseEntity.ok(updatedTank);
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/deleteTank/{tankId}")
    public ResponseEntity<String> deleteTank(@PathVariable Long tankId) {
        try {
            tankApplicationService.deleteTank(tankId);
            return ResponseEntity.ok("Deleted tank successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}
