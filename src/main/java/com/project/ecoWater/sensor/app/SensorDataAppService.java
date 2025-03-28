package com.project.ecoWater.sensor.app;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.domain.DeviceService;
import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.sensor.domain.SensorDataRepository;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorDataAppService extends DeviceService<SensorData> {

    private final SensorDataRepository sensorDataRepository;

    public SensorDataAppService(DeviceRepository repository, UserRepository userRepository
    , SensorDataRepository sensorDataRepository) {
        super(repository, userRepository);
        this.sensorDataRepository = sensorDataRepository;
    }

    @Transactional
    public SensorData createSensorData(SensorData sensorData, String email) {
        validateAndAssignUser(sensorData,email,entity->{
            sensorData.setDevice(entity.getDevice());
        },"sensorData");

        sensorData.setMeasurementTime(Timestamp.valueOf(LocalDateTime.now()));
        return sensorDataRepository.save(sensorData);
    }

    public SensorData getSensorData(Long id) {
        if(!sensorDataRepository.existsById(id)){
            throw new IllegalArgumentException("SensorData not found");
        }
        return sensorDataRepository.findById(id);
    }

    public List<SensorData> getAllSensorData(){
        return sensorDataRepository.findAll();
    }

    @Override
    protected DeviceDTO getDeviceFromEntity(SensorData entity) {
        return entity.getDevice();
    }
}
