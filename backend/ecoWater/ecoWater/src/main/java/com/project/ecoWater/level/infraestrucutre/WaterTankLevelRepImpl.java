package com.project.ecoWater.level.infraestrucutre;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.domain.WaterTankLevelRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
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
    public Optional<WaterTankLevel> findByEmail(String email) {
        return waterTankLevelJpaRepo.findTopLevelOfMainTankByEmail(email).map(WaterTankLevelMapper::toWaterTankLevel);
    }

    @Override
    public List<WaterTankLevel> findAll(String email) {
        return waterTankLevelJpaRepo.findAllByUser(email).stream().map(WaterTankLevelMapper::toWaterTankLevel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return waterTankLevelJpaRepo.existsById(id);
    }

    @Override
    public float findLatestWaterLevelByTankId(Long tankId) {
        return waterTankLevelJpaRepo.findLatestWaterLevelByTankId(tankId);
    }

    @Override
    public float findLastFillPercentageByTankId(Long tankId) {
        return waterTankLevelJpaRepo.findLastFillPercentageByTankId(tankId);
    }
}
