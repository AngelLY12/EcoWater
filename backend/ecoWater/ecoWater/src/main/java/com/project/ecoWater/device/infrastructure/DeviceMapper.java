package com.project.ecoWater.device.infrastructure;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceType;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;

public class DeviceMapper {

    public static Device deviceEntityToDevice(DeviceEntity deviceEntity) {
        return Device.builder()
                .deviceId(deviceEntity.getDeviceId())
                .tank(TankMapper.tankEntityToTankDTO(deviceEntity.getTank()))
                .deviceName(deviceEntity.getDeviceName())
                .deviceType(deviceEntity.getDeviceType())
                .deviceLocation(deviceEntity.getDeviceLocation())
                .creationRegister(deviceEntity.getCreationRegister())
                .user(UserMapper.userEntityToUserDTO(deviceEntity.getUser()))
                .ssid(deviceEntity.getSsid())
                .build();
    }
    public static DeviceEntity deviceToDeviceEntity(Device device) {
        return DeviceEntity.builder()
                .deviceId(device.getDeviceId())
                .tank(TankMapper.tankDTOToTankEntity(device.getTank()))
                .deviceName(device.getDeviceName())
                .deviceType(device.getDeviceType())
                .deviceLocation(device.getDeviceLocation())
                .creationRegister(device.getCreationRegister())
                .user(UserMapper.userDTOToUserEntity(device.getUser()))
                .ssid(device.getSsid())
                .build();
    }

    public static DeviceDTO deviceEntityToDeviceDTO(DeviceEntity device) {
        return DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .tank(TankMapper.tankEntityToTankDTO(device.getTank()))
                .user(UserMapper.userEntityToUserDTO(device.getUser()))
                .build();
    }

    public static DeviceEntity deviceDTOToDeviceEntity(DeviceDTO deviceDTO) {
        return DeviceEntity.builder()
                .deviceId(deviceDTO.getDeviceId())
                .tank(TankMapper.tankDTOToTankEntity(deviceDTO.getTank()))
                .user(UserMapper.userDTOToUserEntity(deviceDTO.getUser()))
                .build();
    }
    public static DeviceDTO deviceToDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .tank(device.getTank())
                .user(device.getUser())
                .build();
    }

}
