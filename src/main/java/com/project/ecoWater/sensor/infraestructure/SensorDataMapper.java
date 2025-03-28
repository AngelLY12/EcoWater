package com.project.ecoWater.sensor.infraestructure;

import com.project.ecoWater.device.infrastructure.DeviceMapper;
import com.project.ecoWater.sensor.domain.SensorData;

public class SensorDataMapper {

    public static SensorData sensorDataEntityToSensorData(SensorDataEntity sensorDataEntity) {
        return SensorData.builder()
                .sensorId(sensorDataEntity.getSensorId())
                .device(DeviceMapper.deviceEntityToDeviceDTO(sensorDataEntity.getDevice()))
                .distance(sensorDataEntity.getDistance())
                .measurementTime(sensorDataEntity.getMeasurementTime())
                .build();
    }

    public static SensorDataEntity sensorDatatoSensorDataEntity(SensorData sensorData) {
        return SensorDataEntity.builder()
                .sensorId(sensorData.getSensorId())
                .device(DeviceMapper.deviceDTOToDeviceEntity(sensorData.getDevice()))
                .distance(sensorData.getDistance())
                .measurementTime(sensorData.getMeasurementTime())
                .build();
    }
}
