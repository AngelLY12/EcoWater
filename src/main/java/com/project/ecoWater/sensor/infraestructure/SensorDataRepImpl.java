package com.project.ecoWater.sensor.infraestructure;

import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.sensor.domain.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SensorDataRepImpl implements SensorDataRepository {
    private final SensorDataJpaRep sensorDataJpaRep;

    @Override
    public SensorData save(SensorData sensorData) {
        SensorDataEntity sensorDataEntity = SensorDataMapper.sensorDatatoSensorDataEntity(sensorData);
        SensorDataEntity savedSensorDataEntity = sensorDataJpaRep.save(sensorDataEntity);
        return SensorDataMapper.sensorDataEntityToSensorData(savedSensorDataEntity);
    }

    @Override
    public SensorData findById(Long id) {
        return sensorDataJpaRep.findById(id).map(SensorDataMapper::sensorDataEntityToSensorData).orElse(null);
    }

    @Override
    public List<SensorData> findAll() {
        return sensorDataJpaRep.findAll().stream().map(SensorDataMapper::sensorDataEntityToSensorData).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return sensorDataJpaRep.existsById(id);
    }
}
