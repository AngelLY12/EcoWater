package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankFilling;

public class TankMapper {

    public static Tank tankEntityToTank(TankEntity tankEntity) {
        return Tank.builder()
                .tankName(tankEntity.getTankName())
                .capacity(tankEntity.getCapacity())
                .typeFilling(tankEntity.getTypeFilling())
                .build();
    }
    public static TankEntity tankToTankEntity(Tank tank) {
        return TankEntity.builder()
                .tankName(tank.getTankName())
                .capacity(tank.getCapacity())
                .typeFilling(tank.getTypeFilling())
                .build();
    }

    public static TankFilling tankFillingEntityToTankFilling(TankFillingEntity tankFillingEntity) {
        return TankFilling.builder()
                .device(tankFillingEntity.getDevice())
                .tank(tankEntityToTank(tankFillingEntity.getTank()))
                .totalVolume(tankFillingEntity.getTotalVolume())
                .startDate(tankFillingEntity.getStartDate())
                .endDate(tankFillingEntity.getEndDate())
                .build();
    }

    public static TankFillingEntity tankFillingToTankFillingEntity(TankFilling tankFilling) {
        return TankFillingEntity.builder()
                .device(tankFilling.getDevice())
                .tank(tankToTankEntity(tankFilling.getTank()))
                .totalVolume(tankFilling.getTotalVolume())
                .startDate(tankFilling.getStartDate())
                .endDate(tankFilling.getEndDate())
                .build();
    }
}
