package com.project.ecoWater.level.domain;

import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class WaterTankLevel {
    private Long levelId;
    private TankDTO tank;
    private BigDecimal waterLevel;
    private Timestamp dateMeasurement;
    private BigDecimal sensorDistance;
    private BigDecimal fillPercentage;
}
