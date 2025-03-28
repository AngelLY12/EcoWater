package com.project.ecoWater.device.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {
    Optional<Device> getDevice(UUID deviceId);
    List<Device> getAllDevices();
    Device saveDevice(Device device);
    void deleteDevice(UUID deviceId);
    boolean existsDeviceById(UUID deviceId);
    Optional<Device> updateDevice(Device device);
}
