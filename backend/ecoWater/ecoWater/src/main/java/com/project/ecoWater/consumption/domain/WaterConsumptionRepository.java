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
    List<WaterConsumption> findConsumptionByLocation(String email);
    boolean existsById(Long id);

    Optional<WaterConsumption> findFirstMeasurementForMainTank(String email);
    List<WaterConsumption> findAllMainTankLevelsByUser(String email);
    List<WaterConsumption> findAllMainTankLevelsByDate(String email, LocalDate date);
    List<WaterConsumption> findAllMainTankLevelsByDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
