package com.project.ecoWater.filling.infraestructure;


import com.project.ecoWater.filling.app.TankFillingAppService;
import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.level.domain.WaterTankLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @GetMapping("/getMainTankFillingsByDate")
    public ResponseEntity<List<TankFilling>> getMainTankFillingsByUserAndDate(
            @RequestParam("date") String date,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<TankFilling> result = tankFillingAppService.findMainTankFillingsByUserAndDate(userDetails.getUsername(), localDate);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getMainTankFillingsByDateTime")
    public ResponseEntity<List<TankFilling>> getMainTankFillingsByUserAndDateTime(
            @RequestParam("date") String date,
            @RequestParam("startHour") String startHour, // formato HH:mm
            @RequestParam("endHour") String endHour,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Convertir los valores recibidos a LocalDateTime
            LocalDate localDate = LocalDate.parse(date);
            LocalTime startTime = LocalTime.parse(startHour);
            LocalTime endTime = LocalTime.parse(endHour);

            LocalDateTime startDateTime = LocalDateTime.of(localDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(localDate, endTime);

            List<TankFilling> result = tankFillingAppService.findMainTankFillingsByUserAndDateTime(
                    userDetails.getUsername(), startDateTime, endDateTime
            );

            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getMainTankFillingsByDateTime2")
    public ResponseEntity<List<TankFilling>> getMainTankFillingsByUserAndDateTime2(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam("startHour") @DateTimeFormat(pattern = "HH:mm") String startHour,
            @RequestParam("endHour") @DateTimeFormat(pattern = "HH:mm") String endHour,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Convertir los valores recibidos a LocalDateTime
            LocalTime startTime = LocalTime.parse(startHour);
            LocalTime endTime = LocalTime.parse(endHour);

            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            List<TankFilling> result = tankFillingAppService.findMainTankFillingsByUserAndDateTime(
                    userDetails.getUsername(), startDateTime, endDateTime
            );

            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
