package com.project.ecoWater.consumption.infraestructure;

import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.device.infrastructure.DeviceMapper;

public class WaterConsumptionMapper {

    public static WaterConsumption waterConsumptionEntityToWaterConsumption
            (WaterConsumptionEntity wcEntity) {
        return WaterConsumption.builder()
                .consumptionId(wcEntity.getConsumptionId())
                .flowRate(wcEntity.getFlowRate())
                .totalConsumption(wcEntity.getTotalConsumption())
                .device(DeviceMapper.deviceEntityToDeviceDTO(wcEntity.getDevice()))
                .startedDate(wcEntity.getStartedDate())
                .build();
    }

    public static WaterConsumptionEntity waterConsumptionToWaterConsumptionEntity
            (WaterConsumption wc) {
        return WaterConsumptionEntity.builder()
                .consumptionId(wc.getConsumptionId())
                .flowRate(wc.getFlowRate())
                .totalConsumption(wc.getTotalConsumption())
                .startedDate(wc.getStartedDate())
                .device(DeviceMapper.deviceDTOToDeviceEntity(wc.getDevice()))
                .build();
    }

}
