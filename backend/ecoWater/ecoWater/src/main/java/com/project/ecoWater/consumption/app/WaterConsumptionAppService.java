package com.project.ecoWater.consumption.app;


import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.consumption.domain.WaterConsumptionRepository;
import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.domain.DeviceService;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaterConsumptionAppService extends DeviceService<WaterConsumption> {

    private final WaterConsumptionRepository waterConsumptionRepository;

    public WaterConsumptionAppService(DeviceRepository repository,
                                      UserRepository userRepository,
                                      WaterConsumptionRepository waterConsumptionRepository) {
        super(repository, userRepository);
        this.waterConsumptionRepository=waterConsumptionRepository;
    }

    @Transactional
    public WaterConsumption create(WaterConsumption waterConsumption, String email) {
        validateAndAssignUser(waterConsumption, email, entity->{
            waterConsumption.setDevice(entity.getDevice());
                },"Consumption");
        waterConsumption.setStartedDate(Timestamp.valueOf(LocalDateTime.now()));
        return waterConsumptionRepository.save(waterConsumption);
    }

    public WaterConsumption findById(Long id) {
        if(!waterConsumptionRepository.existsById(id)) {
            throw new IllegalArgumentException("No such water consumption");
        }
        return waterConsumptionRepository.findById(id);
    }

    public List<WaterConsumption> findAll() {
        return waterConsumptionRepository.findAll();
    }



    @Override
    protected DeviceDTO getDeviceFromEntity(WaterConsumption entity) {
        return entity.getDevice();
    }
}
