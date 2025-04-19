package com.project.ecoWater.level.app;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.domain.WaterTankLevelRepository;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.domain.TankService;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WaterTankLevelAppService extends TankService<WaterTankLevel> {
    private final WaterTankLevelRepository waterTankLevelRepository;
    private final TankRepository tankRepository;

    public WaterTankLevelAppService(TankRepository tankRepository, UserRepository userRepository, WaterTankLevelRepository waterTankLevelRepository) {
        super(tankRepository, userRepository);
        this.waterTankLevelRepository = waterTankLevelRepository;
        this.tankRepository = tankRepository;
    }

    public WaterTankLevel save(WaterTankLevel waterTankLevel, String email) {
        validateAndAssignUser(waterTankLevel, email, entity -> {
            TankDTO tank= entity.getTank();
                    if (tank != null) {
                        Tank persistedTank = tankRepository.findById(tank.getTankId())
                                .orElseThrow(() -> new IllegalArgumentException("Tank not found"));
                        float waterLevel= calculateWaterLevel(persistedTank.getTankId());
                        float fillPercentage= calculateFillPercentage(persistedTank.getTankId());
                        waterTankLevel.setWaterLevel(waterLevel);
                        waterTankLevel.setFillPercentage(fillPercentage);
                        waterTankLevel.setTank(TankMapper.tankToTankDTO(persistedTank));
        }},"Level");

        waterTankLevel.setDateMeasurement(Timestamp.valueOf(LocalDateTime.now()));
        return waterTankLevelRepository.save(waterTankLevel);
    }

    public Optional<WaterTankLevel> findByEmail(String email) {
        return Optional.ofNullable(waterTankLevelRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No tank level found for the provided email.")));
    }
    public List<WaterTankLevel> findAll(String email) {
        List<WaterTankLevel> allLevels = waterTankLevelRepository.findAll(email);

        if (allLevels.isEmpty()) {
            throw new IllegalArgumentException("No tank levels found for this user.");
        }

        return allLevels;
    }

    @Override
    protected TankDTO getTankFromEntity(WaterTankLevel entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null. Cannot extract tank info.");
        }

        return entity.getTank();
    }


    public float calculateWaterLevel(Long levelId){
        return waterTankLevelRepository.findLatestWaterLevelByTankId(levelId);
    }
    public float calculateFillPercentage(Long levelId){
        return waterTankLevelRepository.findLastFillPercentageByTankId(levelId);
    }





}
