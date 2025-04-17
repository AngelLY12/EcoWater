package com.project.ecoWater.consumption.infraestructure;

import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.device.infrastructure.DeviceMapper;

public class WaterConsumptionMapper {

    public static WaterConsumption waterConsumptionEntityToWaterConsumption
            (WaterConsumptionEntity wcEntity) {
        return WaterConsumption.builder()
                .consumptionId(wcEntity.getConsumptionId())
                .flowRate(wcEntity.getFlowRate())
                .device(DeviceMapper.deviceEntityToDeviceDTO(wcEntity.getDevice()))
                .endedDate(wcEntity.getEndedDate())
                .startedDate(wcEntity.getStartedDate())
                .build();
    }

    public static WaterConsumptionEntity waterConsumptionToWaterConsumptionEntity
            (WaterConsumption wc) {
        return WaterConsumptionEntity.builder()
                .consumptionId(wc.getConsumptionId())
                .flowRate(wc.getFlowRate())
                .startedDate(wc.getStartedDate())
                .endedDate(wc.getEndedDate())
                .device(DeviceMapper.deviceDTOToDeviceEntity(wc.getDevice()))
                .build();
    }
}
