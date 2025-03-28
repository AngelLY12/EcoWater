package com.project.ecoWater.device.app;

import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.infrastructure.DeviceRequest;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceApplicationService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    public Optional<Device> getDevice(UUID deviceId) {
        if(!deviceRepository.existsDeviceById(deviceId)) {
            throw new IllegalArgumentException("Device not found");
        }
        return deviceRepository.getDevice(deviceId);
    }

    @Transactional
    public Device createDevice(DeviceRequest deviceRequest, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        UserDTO userDevice= UserMapper.userToUserDTO(user);
        System.out.printf("USUARIO ENCONTRADO: %s\n", user);

        Device newDevice = Device.builder()
                .user(userDevice)
                .deviceType(deviceRequest.getDevice_type())
                .deviceName(deviceRequest.getDevice_name())
                .deviceLocation(deviceRequest.getDevice_location())
                .creationRegister(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        return deviceRepository.saveDevice(newDevice);
    }
    @Transactional
    public Optional<Device> updateDevice(Device device, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        UserDTO userDevice= UserMapper.userToUserDTO(user);
        device.setUser(userDevice);
        Optional<Device> existingDevice = deviceRepository.getDevice(device.getDeviceId());
        if(existingDevice.isPresent()) {
            Device updatedDevice = existingDevice.get();
            if(device.getDeviceName()!=null) {
                updatedDevice.setDeviceName(device.getDeviceName());
            }
            if(device.getDeviceLocation()!=null) {
                updatedDevice.setDeviceLocation(device.getDeviceLocation());
            }
            if(device.getDeviceType()!=null) {
                updatedDevice.setDeviceType(device.getDeviceType());
            }
            Device updatedDeviceSaved = deviceRepository.saveDevice(updatedDevice);
            return Optional.of(updatedDeviceSaved);
        }
        return Optional.empty();
    }
    public void deleteDevice(UUID deviceId) {
        if(!deviceRepository.existsDeviceById(deviceId)) {
            throw new IllegalArgumentException("Device not found");
        }
        deviceRepository.deleteDevice(deviceId);
    }
    public List<Device> getAllDevices() {
        return deviceRepository.getAllDevices();
    }
    public boolean existDeviceById(UUID deviceId) {
        return deviceRepository.existsDeviceById(deviceId);
    }

}
