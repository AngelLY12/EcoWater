package com.project.ecoWater.consumption.app;


import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.consumption.domain.WaterConsumptionRepository;
import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.domain.DeviceService;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<WaterConsumption> findAll(String email) {
        return waterConsumptionRepository.findAll(email);
    }

    public List<WaterConsumption> findConsumptionByLocation(String email) {
        if(waterConsumptionRepository.findConsumptionByLocation(email) == null) {
            throw new IllegalArgumentException("No such water consumption");
        }
        return waterConsumptionRepository.findConsumptionByLocation(email);
    }


    public Optional<WaterConsumption> findFirstMeasurementForMainTank(String email) {
        return Optional.ofNullable(waterConsumptionRepository.findFirstMeasurementForMainTank(email)
                .orElseThrow(() -> new IllegalArgumentException("No hay datos en el consumo del agua.")));
    }

    public List<WaterConsumption> findAllMainTankLevelsByUser(String email) {
        List<WaterConsumption> allLevels = waterConsumptionRepository.findAllMainTankLevelsByUser(email);

        // Retorna lista vacía en lugar de lanzar una excepción
        return allLevels;
    }

    public List<WaterConsumption> findAllMainTankLevelsByDate(String email, LocalDate date) {
        List<WaterConsumption> levelsByDate = waterConsumptionRepository.findAllMainTankLevelsByDate(email, date);

        // Retorna lista vacía si no hay registros
        return levelsByDate;
    }

    public List<WaterConsumption> findAllMainTankLevelsByDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<WaterConsumption> levelsByDateTime = waterConsumptionRepository.findAllMainTankLevelsByDateTime(email, startDateTime, endDateTime);

        return levelsByDateTime;
    }



    @Override
    protected DeviceDTO getDeviceFromEntity(WaterConsumption entity) {
        return entity.getDevice();
    }
}
