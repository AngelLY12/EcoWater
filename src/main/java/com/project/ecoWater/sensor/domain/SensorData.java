package com.project.ecoWater.sensor.domain;


import com.project.ecoWater.device.app.DeviceDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class SensorData {
    private Long sensorId;
    private DeviceDTO device;
    private BigDecimal distance;
    private Timestamp measurementTime;

}
