package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.sensor.infraestructure.SensorDataMapper;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;

public class TankMapper {

    public static Tank tankEntityToTank(TankEntity tankEntity) {
        return Tank.builder()
                .tankId(tankEntity.getTankId())
                .tankName(tankEntity.getTankName())
                .capacity(tankEntity.getCapacity())
                .fillingType(tankEntity.getFillingType())
                .tankHeight(tankEntity.getTankHeight())
                .isMain(tankEntity.getIsMain())
                .createdAt(tankEntity.getCreatedAt())
                .user(UserMapper.userEntityToUserDTO(tankEntity.getUser()))
                .build();
    }
    public static TankEntity tankToTankEntity(Tank tank) {
        return TankEntity.builder()
                .tankId(tank.getTankId())
                .tankName(tank.getTankName())
                .capacity(tank.getCapacity())
                .fillingType(tank.getFillingType())
                .tankHeight(tank.getTankHeight())
                .createdAt(tank.getCreatedAt())
                .isMain(tank.getIsMain())
                .user(UserMapper.userDTOToUserEntity(tank.getUser()))
                .build();
    }

    public static TankDTO tankToTankDTO(Tank tank) {
        return TankDTO.builder()
                .tankId(tank.getTankId())
                .user(tank.getUser())
                .capacity(tank.getCapacity())
                .tankHeight(tank.getTankHeight())
                .build();
    }

    public static TankEntity tankDTOToTankEntity(TankDTO tankDTO) {
        return TankEntity.builder()
                .tankId(tankDTO.getTankId())
                .user(UserMapper.userDTOToUserEntity(tankDTO.getUser()))
                .capacity(tankDTO.getCapacity())
                .tankHeight(tankDTO.getTankHeight())
                .isMain(tankDTO.getIsMain())
                .build();
    }

    public static TankDTO tankEntityToTankDTO(TankEntity tankEntity) {
        return TankDTO.builder()
                .tankId(tankEntity.getTankId())
                .user(UserMapper.userEntityToUserDTO(tankEntity.getUser()))
                .capacity(tankEntity.getCapacity())
                .tankHeight(tankEntity.getTankHeight())
                .isMain(tankEntity.getIsMain())
                .build();
    }


}
