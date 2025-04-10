package com.project.ecoWater.device.infrastructure;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceType;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;

public class DeviceMapper {

    public static Device deviceEntityToDevice(DeviceEntity deviceEntity) {
        return Device.builder()
                .deviceId(deviceEntity.getDeviceId())
                .deviceName(deviceEntity.getDeviceName())
                .deviceType(deviceEntity.getDeviceType())
                .deviceLocation(deviceEntity.getDeviceLocation())
                .creationRegister(deviceEntity.getCreationRegister())
                .user(UserMapper.userEntityToUserDTO(deviceEntity.getUser()))
                .build();
    }
    public static DeviceEntity deviceToDeviceEntity(Device device) {
        return DeviceEntity.builder()
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .deviceType(device.getDeviceType())
                .deviceLocation(device.getDeviceLocation())
                .creationRegister(device.getCreationRegister())
                .user(UserMapper.userDTOToUserEntity(device.getUser()))
                .build();
    }

    public static DeviceDTO deviceEntityToDeviceDTO(DeviceEntity device) {
        return DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .user(UserMapper.userEntityToUserDTO(device.getUser()))
                .build();
    }

    public static DeviceEntity deviceDTOToDeviceEntity(DeviceDTO deviceDTO) {
        return DeviceEntity.builder()
                .deviceId(deviceDTO.getDeviceId())
                .user(UserMapper.userDTOToUserEntity(deviceDTO.getUser()))
                .build();
    }

}
