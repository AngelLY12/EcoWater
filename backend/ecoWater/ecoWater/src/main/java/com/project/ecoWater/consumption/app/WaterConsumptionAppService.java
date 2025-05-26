package com.project.ecoWater.consumption.app;


import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.consumption.domain.WaterConsumptionRepository;
import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.domain.DeviceService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.notification.alert.MonitoringService;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class WaterConsumptionAppService extends DeviceService<WaterConsumption> {

    private final WaterConsumptionRepository waterConsumptionRepository;
    private final MonitoringService monitoringService;

    public WaterConsumptionAppService(DeviceRepository repository,
                                      UserRepository userRepository,
                                      WaterConsumptionRepository waterConsumptionRepository,
                                      MonitoringService monitoringService) {
        super(repository, userRepository);
        this.waterConsumptionRepository=waterConsumptionRepository;
        this.monitoringService=monitoringService;
    }

    @Transactional
    public WaterConsumption create(WaterConsumption waterConsumption, String email) {
        validateAndAssignUser(waterConsumption, email, entity->{
            waterConsumption.setDevice(entity.getDevice());
                },"Consumption");
        waterConsumption.setStartedDate(Timestamp.valueOf(LocalDateTime.now()));
        WaterConsumption saved =waterConsumptionRepository.save(waterConsumption);
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        Double consumos = waterConsumptionRepository
                .findByUserEmailAndStartedDateBetween(email, startOfDay, endOfDay);


        System.out.println("Consumos log: " + consumos);
        System.out.println("Consumos log: " + consumos.floatValue());
        monitoringService.checkWaterConsumption(email, consumos.floatValue());
        return saved;
    }

    public WaterConsumption findById(Long id) {
        if(!waterConsumptionRepository.existsById(id)) {
            throw new IllegalArgumentException("No such water consumption");
        }
        return waterConsumptionRepository.findById(id);
    }

    public List<WaterConsumption> findAll(String email) {
        return waterConsumptionRepository.findAll(email);
    }

    public List<WaterConsumption> findConsumptionByDate(String email, LocalDateTime startDate, LocalDateTime endDate) {
        if(waterConsumptionRepository.findConsumptionByDate(email,startDate,endDate) == null) {
            throw new IllegalArgumentException("No such water consumption");
        }
        return waterConsumptionRepository.findConsumptionByDate(email,startDate,endDate);
    }


    @Override
    protected DeviceDTO getDeviceFromEntity(WaterConsumption entity) {
        return entity.getDevice();
    }
}
