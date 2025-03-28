package com.project.ecoWater.tank.infrastructure;

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
                .user(UserMapper.userDTOToUserEntity(tank.getUser()))
                .build();
    }

    public static TankDTO tankToTankDTO(Tank tank) {
        return TankDTO.builder()
                .tankId(tank.getTankId())
                .build();
    }

    public static TankEntity tankDTOToTankEntity(TankDTO tankDTO) {
        return TankEntity.builder()
                .tankId(tankDTO.getTankId())
                .user(UserMapper.userDTOToUserEntity(tankDTO.getUser()))
                .build();
    }

    public static TankDTO tankEntityToTankDTO(TankEntity tankEntity) {
        return TankDTO.builder()
                .tankId(tankEntity.getTankId())
                .user(UserMapper.userEntityToUserDTO(tankEntity.getUser()))
                .build();
    }


}
