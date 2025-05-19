package com.project.ecoWater.level.infraestrucutre;


import com.project.ecoWater.level.app.WaterTankLevelAppService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.notification.alert.MonitoringService;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.app.UserApplicationService;
import com.project.ecoWater.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @GetMapping("/getLevelsMain")
    public ResponseEntity<List<WaterTankLevel>> getAllMainLevels(@AuthenticationPrincipal UserDetails userDetails) {
        List<WaterTankLevel> result = waterTankLevelAppService.findAllMainTankLevelsByUser(userDetails.getUsername());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getFirstMeasurementDate")
    public ResponseEntity<WaterTankLevel> getFirstMeasurementDate(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<WaterTankLevel> firstMeasurement = waterTankLevelAppService.findFirstMeasurementForMainTank(userDetails.getUsername());
        return firstMeasurement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/getLevelsMainByDate")
    public ResponseEntity<List<WaterTankLevel>> getLevelsMainByDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @AuthenticationPrincipal UserDetails userDetails) {

        List<WaterTankLevel> result = waterTankLevelAppService.findAllMainTankLevelsByDate(userDetails.getUsername(), date);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getLevelsMainByDateTime")
    public ResponseEntity<List<WaterTankLevel>> getLevelsMainByDateTime(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("startHour") @DateTimeFormat(pattern = "HH:mm") String startHour,
            @RequestParam("endHour") @DateTimeFormat(pattern = "HH:mm") String endHour,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            LocalTime startTime = LocalTime.parse(startHour);
            LocalTime endTime = LocalTime.parse(endHour);
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

            List<WaterTankLevel> result = waterTankLevelAppService.findAllMainTankLevelsByDateTime(
                    userDetails.getUsername(), startDateTime, endDateTime);

            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getLevelsMainByDateTime2")
    public ResponseEntity<List<WaterTankLevel>> getLevelsMainByDateTime2(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("startHour") @DateTimeFormat(pattern = "HH:mm") String startHour,
            @RequestParam("endHour") @DateTimeFormat(pattern = "HH:mm") String endHour,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            LocalTime startTime = LocalTime.parse(startHour);
            LocalTime endTime = LocalTime.parse(endHour);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            List<WaterTankLevel> result = waterTankLevelAppService.findAllMainTankLevelsByDateTime(
                    userDetails.getUsername(), startDateTime, endDateTime);

            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
