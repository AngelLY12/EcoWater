package com.project.ecoWater.level.domain;

import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WaterTankLevelRepository {
    WaterTankLevel save(WaterTankLevel waterTankLevel);
    Optional<WaterTankLevel> findByEmail(String email);
    List<WaterTankLevel> findAll(String email);

    boolean existsById(Long id);

    float findLatestWaterLevelByTankId(Long tankId);
    float findLastFillPercentageByTankId(Long tankId);


}
