package com.project.ecoWater.device.infrastructure;

import com.project.ecoWater.device.domain.Device;
import com.project.ecoWater.device.domain.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {
    private final DeviceJpaRepository deviceJpaRepository;
    @Override
    public Optional<Device> getDevice(String mac) {
        return deviceJpaRepository.findById(mac).map(DeviceMapper::deviceEntityToDevice);
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceJpaRepository.findAll().stream().map(DeviceMapper::deviceEntityToDevice).collect(Collectors.toList());
    }

    @Override
    public Device saveDevice(Device device) {
        DeviceEntity deviceEntity= DeviceMapper.deviceToDeviceEntity(device);
        DeviceEntity savedDeviceEntity = deviceJpaRepository.save(deviceEntity);
        return DeviceMapper.deviceEntityToDevice(savedDeviceEntity);
    }

    @Override
    public void deleteDevice(String mac) {
        deviceJpaRepository.deleteById(mac);
    }

    @Override
    public boolean existsDeviceById(String mac) {
        return deviceJpaRepository.existsById(mac);
    }

    @Override
    @Transactional
    public Optional<Device> updateDevice(Device device) {
        DeviceEntity deviceEntity= DeviceMapper.deviceToDeviceEntity(device);
        DeviceEntity savedDeviceEntity = deviceJpaRepository.save(deviceEntity);
        return Optional.ofNullable(DeviceMapper.deviceEntityToDevice(savedDeviceEntity));
    }
}
