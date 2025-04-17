package com.project.ecoWater.consumption.domain;


import com.project.ecoWater.device.app.DeviceDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class WaterConsumption {
    private Long consumptionId;
    private DeviceDTO device;
    private Float flowRate;
    private Timestamp startedDate;
    private Timestamp endedDate;
}
