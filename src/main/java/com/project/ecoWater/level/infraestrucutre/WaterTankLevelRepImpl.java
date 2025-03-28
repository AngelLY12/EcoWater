package com.project.ecoWater.level.infraestrucutre;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.domain.WaterTankLevelRepository;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WaterTankLevelRepImpl implements WaterTankLevelRepository {
    private final WaterTankLevelJpaRepo waterTankLevelJpaRepo;
    @Override
    public WaterTankLevel save(WaterTankLevel waterTankLevel) {
        WaterTankLevelEntity waterTankLevelEntity= WaterTankLevelMapper.toEntityWaterTankLevel(waterTankLevel);
        WaterTankLevelEntity savedWaterTankLevel = waterTankLevelJpaRepo.save(waterTankLevelEntity);
        return WaterTankLevelMapper.toWaterTankLevel(savedWaterTankLevel);
    }

    @Override
    public WaterTankLevel findById(Long id) {
        return waterTankLevelJpaRepo.findById(id).map(WaterTankLevelMapper::toWaterTankLevel).orElse(null);
    }

    @Override
    public List<WaterTankLevel> findAll() {
        return waterTankLevelJpaRepo.findAll().stream().map(WaterTankLevelMapper::toWaterTankLevel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return waterTankLevelJpaRepo.existsById(id);
    }
}
