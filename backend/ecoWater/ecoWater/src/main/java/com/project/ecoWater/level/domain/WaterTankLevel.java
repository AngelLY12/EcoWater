package com.project.ecoWater.level.domain;

import com.project.ecoWater.tank.app.TankDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class WaterTankLevel {
    private Long levelId;
    private TankDTO tank;
    private Float waterLevel;
    private Timestamp dateMeasurement;
    private Float fillPercentage;
}
