package com.project.ecoWater.sensor.infraestructure;


import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.sensor.app.SensorDataAppService;
import com.project.ecoWater.sensor.domain.SensorData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataAppService sensorDataAppService;

    @PostMapping("/addSensorData")
    public ResponseEntity<SensorData> save(@RequestBody SensorData sensorData,
                                           @AuthenticationPrincipal UserDetails userDetails
    ) {
        SensorData result = sensorDataAppService.createSensorData(sensorData, userDetails.getUsername());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getSensorDatas")
    public ResponseEntity<List<SensorData>> getAll() {
        List<SensorData> result = sensorDataAppService.getAllSensorData();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getSensorData/{id}")
    public ResponseEntity<SensorData> getById(@PathVariable Long id) {
        SensorData data= sensorDataAppService.getSensorData(id);
        return ResponseEntity.ok(data);
    }
}
