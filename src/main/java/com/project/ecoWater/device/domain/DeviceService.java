package com.project.ecoWater.device.domain;

import com.project.ecoWater.BaseService;
import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

public abstract class DeviceService<T> extends BaseService<T,DeviceRepository> {

    public DeviceService(DeviceRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }
    @Override
    protected Object getEntityFromGeneric(T entity) {
        return getDeviceFromEntity(entity);
    }

    @Override
    protected boolean existsInRepository(Object entity) {
        DeviceDTO device = (DeviceDTO) entity;
        return device != null && device.getDeviceId() != null && repository.existsDeviceById(device.getDeviceId());
    }

    @Override
    protected void setUserToEntity(Object entity, UserDTO userDTO) {
        DeviceDTO device = (DeviceDTO) entity;
        device.setUser(userDTO);
    }

    protected abstract DeviceDTO getDeviceFromEntity(T entity);

}
