package com.project.ecoWater.level.app;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.domain.WaterTankLevelRepository;
import com.project.ecoWater.notification.alert.MonitoringService;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.domain.TankService;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WaterTankLevelAppService extends TankService<WaterTankLevel> {
    private final WaterTankLevelRepository waterTankLevelRepository;
    private final TankRepository tankRepository;
    private final MonitoringService monitoringService;

    public WaterTankLevelAppService(TankRepository tankRepository,
                                    UserRepository userRepository,
                                    WaterTankLevelRepository waterTankLevelRepository,
                                    MonitoringService monitoringService

    ) {
        super(tankRepository, userRepository);
        this.waterTankLevelRepository = waterTankLevelRepository;
        this.tankRepository = tankRepository;
        this.monitoringService = monitoringService;
    }

    @Transactional
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
        WaterTankLevel savedWaterTankLevel = waterTankLevelRepository.save(waterTankLevel);
        monitoringService.checkTankLevel(email, savedWaterTankLevel.getFillPercentage());
        return savedWaterTankLevel;
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

    public Optional<WaterTankLevel> findFirstMeasurementForMainTank(String email) {
        return Optional.ofNullable(waterTankLevelRepository.findFirstMeasurementForMainTank(email)
                .orElseThrow(() -> new IllegalArgumentException("No hay datos en el consumo del agua.")));
    }

    public List<WaterTankLevel> findAllMainTankLevelsByUser(String email) {
        List<WaterTankLevel> allLevels = waterTankLevelRepository.findAllMainTankLevelsByUser(email);

        // Retorna lista vacía en lugar de lanzar una excepción
        return allLevels;
    }

    public List<WaterTankLevel> findAllMainTankLevelsByDate(String email, LocalDate date) {
        List<WaterTankLevel> levelsByDate = waterTankLevelRepository.findAllMainTankLevelsByDate(email, date);

        // Retorna lista vacía si no hay registros
        return levelsByDate;
    }

    public List<WaterTankLevel> findAllMainTankLevelsByDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<WaterTankLevel> levelsByDateTime = waterTankLevelRepository.findAllMainTankLevelsByDateTime(email, startDateTime, endDateTime);

        return levelsByDateTime;
    }





}
