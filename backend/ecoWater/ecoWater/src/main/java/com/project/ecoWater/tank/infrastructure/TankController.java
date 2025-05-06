package com.project.ecoWater.tank.infrastructure;


import com.project.ecoWater.notification.FirebaseNotificationService;
import com.project.ecoWater.notification.alert.AlertType;
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
    private final FirebaseNotificationService notificationService;

    @PostMapping("/addTank")
    public ResponseEntity<Tank> createTank(@RequestBody Tank tank, @AuthenticationPrincipal UserDetails userDetails) {
        Tank tankSaved = tankApplicationService.saveTank(tank, userDetails.getUsername());
        notificationService.sendNotification(userDetails.getUsername(),"Nuevo tanque creado", "Se ha creado el tanque " + tank.getTankName(), AlertType.FULL_TANK);
        return ResponseEntity.ok(tankSaved);
    }

    @GetMapping("/getTank/{tankId}")
    public ResponseEntity<Tank> getTankById(@PathVariable Long tankId) {
        Tank getTank=tankApplicationService.findTankById(tankId);
        return ResponseEntity.ok(getTank);
    }

    @GetMapping("/getAllTanks")
    public ResponseEntity<List<Tank>> getAllTanks(@AuthenticationPrincipal UserDetails userDetails) {
        List<Tank> getAllTanks=tankApplicationService.findAllTanks(userDetails.getUsername());
        return ResponseEntity.ok(getAllTanks);
    }

    @PatchMapping("/updateTank")
    public ResponseEntity<Optional<Tank>> updateTank(@RequestBody Tank tank, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Tank> updatedTank=tankApplicationService.updateTank(tank, userDetails.getUsername());
        return ResponseEntity.ok(updatedTank);
    }

    @DeleteMapping("/deleteTank/{tankId}")
    public ResponseEntity<String> deleteTank(@PathVariable Long tankId, @AuthenticationPrincipal UserDetails userDetails) {
        tankApplicationService.deleteTank(tankId, userDetails.getUsername());
        return ResponseEntity.ok("Deleted tank successfully");
    }

}
