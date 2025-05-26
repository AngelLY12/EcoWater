package com.project.ecoWater.device.app;

import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceRepository;
import com.project.ecoWater.device.infrastructure.DeviceRequest;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.infrastructure.TankMapper;
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
    private final TankRepository tankRepository;
    public Optional<Device> getDevice(String deviceId) {
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
        Tank tank = tankRepository.findById(deviceRequest.getTank().getTankId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        TankDTO tankDevice = TankMapper.tankToTankDTO(tank);
        Device newDevice = Device.builder()
                .deviceId(deviceRequest.getDevice_id())
                .tank(tankDevice)
                .user(userDevice)
                .deviceType(deviceRequest.getDevice_type())
                .deviceName(deviceRequest.getDevice_name())
                .deviceLocation(deviceRequest.getDevice_location())
                .creationRegister(Timestamp.valueOf(LocalDateTime.now()))
                .ssid(deviceRequest.getSsid())
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

    @Transactional
    public Optional<Device> updateStatus(Device device, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));
        UserDTO userDevice= UserMapper.userToUserDTO(user);
        device.setUser(userDevice);
        Optional<Device> existingDevice = deviceRepository.getDevice(device.getDeviceId());
        if(existingDevice.isPresent()) {
            Device updatedDevice = existingDevice.get();
            if(device.getConnected()!=null) {
                updatedDevice.setConnected(device.getConnected());
            }
            if(device.getLastSeen()!=null) {
                updatedDevice.setLastSeen(device.getLastSeen());
            }
            Device updatedDeviceSaved = deviceRepository.saveDevice(updatedDevice);
            return Optional.of(updatedDeviceSaved);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteDevice(String deviceId, String email) {
        if(!deviceRepository.existsDeviceById(deviceId)) {
            throw new IllegalArgumentException("Device not found");
        }
        deviceRepository.deleteDevice(deviceId, email);
    }
    public List<Device> getAllDevices(String email) {
        List<Device> devices = deviceRepository.getAllDevices(email);

        if (devices.isEmpty()) {
            throw new IllegalArgumentException("No devices found for this user.");
        }

        return devices;
    }
    public boolean existDeviceById(String deviceId) {
        if (!deviceRepository.existsDeviceById(deviceId)) {
            throw new IllegalArgumentException("Device with ID " + deviceId + " does not exist.");
        }
    return true;
    }

}
