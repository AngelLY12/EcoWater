package com.project.ecoWater.device.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {
    Optional<Device> getDevice(String deviceId);
    List<Device> getAllDevices(String email);
    Device saveDevice(Device device);
    void deleteDevice(String deviceId, String email);
    boolean existsDeviceById(String deviceId);
    Optional<Device> updateDevice(Device device);
    Optional<Device> updateStatus(Device device, String email);
}
