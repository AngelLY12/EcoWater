package com.project.ecoWater.sensor.domain;


import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.tank.app.TankDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class SensorData {
    private Long sensorId;
    private DeviceDTO device;
    private Float distance;
    private Timestamp measurementTime;
}
