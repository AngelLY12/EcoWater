package com.project.ecoWater.consumption.domain;

import java.util.List;

public interface WaterConsumptionRepository {
    WaterConsumption save(WaterConsumption wc);
    WaterConsumption findById(Long id);
    List<WaterConsumption> findAll();
    boolean existsById(Long id);
}
