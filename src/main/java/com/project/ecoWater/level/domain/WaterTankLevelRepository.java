package com.project.ecoWater.level.domain;

import java.util.List;

public interface WaterTankLevelRepository {
    WaterTankLevel save(WaterTankLevel waterTankLevel);
    WaterTankLevel findById(Long id);
    List<WaterTankLevel> findAll();

    boolean existsById(Long id);
}
