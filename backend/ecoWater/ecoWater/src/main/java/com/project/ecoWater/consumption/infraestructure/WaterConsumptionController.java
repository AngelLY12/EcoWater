package com.project.ecoWater.consumption.infraestructure;


import com.project.ecoWater.consumption.app.WaterConsumptionAppService;
import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.filling.domain.TankFilling;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumption")
@RequiredArgsConstructor
public class WaterConsumptionController {

    private final WaterConsumptionAppService waterConsumptionAppService;


    @PostMapping("/addConsumption")
    public ResponseEntity<WaterConsumption> save(@RequestBody WaterConsumption waterConsumption,
                                            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            WaterConsumption result = waterConsumptionAppService.create(waterConsumption, userDetails.getUsername());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getConsumptions")
    public ResponseEntity<List<WaterConsumption>> getAll() {
        try {
            List<WaterConsumption> result = waterConsumptionAppService.findAll();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getConsumption/{id}")
    public ResponseEntity<WaterConsumption> getById(@PathVariable Long id) {
        try {
            WaterConsumption consumption= waterConsumptionAppService.findById(id);
            return ResponseEntity.ok(consumption);
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
