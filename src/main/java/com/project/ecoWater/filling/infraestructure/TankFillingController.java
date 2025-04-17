package com.project.ecoWater.filling.infraestructure;


import com.project.ecoWater.filling.app.TankFillingAppService;
import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.level.domain.WaterTankLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filling")
@RequiredArgsConstructor
public class TankFillingController {
    private final TankFillingAppService tankFillingAppService;
    @PostMapping("/addFilling")
    public ResponseEntity<TankFilling> save(@RequestBody TankFilling tankFilling,
                                            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            TankFilling result = tankFillingAppService.save(tankFilling, userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addEndDate/{id}")
    public ResponseEntity<String> addEndDate(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails){
        try {
            tankFillingAppService.confirmTankFull(id, userDetails.getUsername());
            return ResponseEntity.ok("Fecha de finalización de llenado añadida");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getFillings")
    public ResponseEntity<List<TankFilling>> getAll() {
        try {
            List<TankFilling> result = tankFillingAppService.findAll();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getFilling/{id}")
    public ResponseEntity<TankFilling> getById(@PathVariable Long id) {
        try {
            TankFilling filling= tankFillingAppService.findById(id);
            return ResponseEntity.ok(filling);
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
