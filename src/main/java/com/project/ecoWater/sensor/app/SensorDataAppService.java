package com.project.ecoWater.sensor.app;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.domain.DeviceService;
import com.project.ecoWater.device.infrastructure.DeviceMapper;
import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.sensor.domain.SensorDataRepository;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.domain.TankService;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorDataAppService extends DeviceService<SensorData> {

    private final SensorDataRepository sensorDataRepository;
    private final TankRepository tankRepository;
    private final DeviceRepository deviceRepository;

    public SensorDataAppService(DeviceRepository repository, UserRepository userRepository,
                                SensorDataRepository sensorDataRepository, TankRepository tankRepository) {
        super(repository, userRepository);
        this.sensorDataRepository = sensorDataRepository;
        this.tankRepository = tankRepository;
        this.deviceRepository = repository;
    }

    @Transactional
    public SensorData createSensorData(SensorData sensorData, String email) {

        validateAndAssignUser(
                sensorData,
                email,
                device -> {
                        DeviceDTO deviceDTO= device.getDevice();
        if (deviceDTO != null) {
            Device persistedDevice = deviceRepository.getDevice(deviceDTO.getDeviceId())
                    .orElseThrow(() -> new IllegalArgumentException("Tank not found"));
            sensorData.setDevice(DeviceMapper.deviceToDeviceDTO(persistedDevice));
        }},
                "device");

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
        List<SensorData> sensorDataList = sensorDataRepository.findAll();
        if(sensorDataList.isEmpty()){
            throw new IllegalArgumentException("SensorDatas not found");
        }
        return sensorDataList;
    }


    @Override
    protected DeviceDTO getDeviceFromEntity(SensorData entity) {
        if(entity == null){
            throw new IllegalArgumentException("SensorData not found");
        }
        return entity.getDevice();
    }
}
