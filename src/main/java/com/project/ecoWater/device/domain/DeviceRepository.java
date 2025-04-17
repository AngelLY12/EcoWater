package com.project.ecoWater.device.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository {
    Optional<Device> getDevice(String mac);
    List<Device> getAllDevices();
    Device saveDevice(Device device);
    void deleteDevice(String mac);
    boolean existsDeviceById(String mac);
    Optional<Device> updateDevice(Device device);
}
