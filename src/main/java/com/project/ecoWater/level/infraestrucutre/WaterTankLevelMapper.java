package com.project.ecoWater.level.infraestrucutre;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.tank.infrastructure.TankMapper;

public class WaterTankLevelMapper {
    public static WaterTankLevel toWaterTankLevel(WaterTankLevelEntity waterTankLevelEntity) {
        return WaterTankLevel.builder()
                .levelId(waterTankLevelEntity.getLevelId())
                .tank(TankMapper.tankEntityToTankDTO(waterTankLevelEntity.getTank()))
                .waterLevel(waterTankLevelEntity.getWaterLevel())
                .dateMeasurement(waterTankLevelEntity.getDateMeasurement())
                .sensorDistance(waterTankLevelEntity.getSensorDistance())
                .fillPercentage(waterTankLevelEntity.getFillPercentage())
                .build();
    }

    public static WaterTankLevelEntity toEntityWaterTankLevel(WaterTankLevel waterTankLevel) {
        return WaterTankLevelEntity.builder()
                .levelId(waterTankLevel.getLevelId())
                .tank(TankMapper.tankDTOToTankEntity(waterTankLevel.getTank()))
                .waterLevel(waterTankLevel.getWaterLevel())
                .dateMeasurement(waterTankLevel.getDateMeasurement())
                .sensorDistance(waterTankLevel.getSensorDistance())
                .fillPercentage(waterTankLevel.getFillPercentage())
                .build();
    }

}
