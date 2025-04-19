package com.project.ecoWater.filling.infraestructure;

import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.tank.infrastructure.TankMapper;

public class TankFillingMapper {
    public static TankFilling tankFillingEntityToTankFilling(TankFillingEntity tankFillingEntity) {
        return TankFilling.builder()
                .tank(TankMapper.tankEntityToTankDTO(tankFillingEntity.getTank()))
                .fillingId(tankFillingEntity.getFillingId())
                .finishedDate(tankFillingEntity.getFinishedDate())
                .startedDate(tankFillingEntity.getStartedDate())
                .totalVolume(tankFillingEntity.getTotalVolume())
                .build();
    }
    public static TankFillingEntity tankFillingToTankFillingEntity(TankFilling tankFilling) {
        return TankFillingEntity.builder()
                .fillingId(tankFilling.getFillingId())
                .finishedDate(tankFilling.getFinishedDate())
                .startedDate(tankFilling.getStartedDate())
                .totalVolume(tankFilling.getTotalVolume())
                .tank(TankMapper.tankDTOToTankEntity(tankFilling.getTank()))
                .build();
    }


}
