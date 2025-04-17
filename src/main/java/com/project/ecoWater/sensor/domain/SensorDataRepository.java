package com.project.ecoWater.sensor.domain;


import java.math.BigDecimal;
import java.util.List;

public interface SensorDataRepository {
    SensorData save(SensorData sensorData);
    SensorData findById(Long id);
    List<SensorData> findAll();
    boolean existsById(Long id);
    float findDistanceByTankFillingId(Long tankFillingId);

}
