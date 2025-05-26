package com.project.ecoWater.consumption.domain;

import com.project.ecoWater.level.domain.WaterTankLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaterConsumptionRepository {
    WaterConsumption save(WaterConsumption wc);
    WaterConsumption findById(Long id);
    List<WaterConsumption> findAll(String email);
    List<WaterConsumption> findConsumptionByDate(String email,  LocalDateTime startDate, LocalDateTime endDate);
    boolean existsById(Long id);
    Double findByUserEmailAndStartedDateBetween(String email,LocalDateTime startOfDay,LocalDateTime endOfDay);

}
