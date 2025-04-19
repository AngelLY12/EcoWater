package com.project.ecoWater.tank.domain;


import com.project.ecoWater.BaseService;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.UserRepository;


public abstract class TankService<T> extends BaseService<T, TankRepository> {


    public TankService(TankRepository repository, UserRepository userRepository) {
        super(repository, userRepository);
    }

    @Override
    protected Object getEntityFromGeneric(T entity) {
        return getTankFromEntity(entity);
    }

    @Override
    protected boolean existsInRepository(Object entity) {
        TankDTO tank = (TankDTO) entity;
        return tank != null && tank.getTankId() != null && repository.existsById(tank.getTankId());
    }

    @Override
    protected void setUserToEntity(Object entity, UserDTO userDTO) {
        TankDTO tank = (TankDTO) entity;
        tank.setUser(userDTO);
    }

    protected abstract TankDTO getTankFromEntity(T entity);
}
