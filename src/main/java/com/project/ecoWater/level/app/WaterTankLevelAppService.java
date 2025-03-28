package com.project.ecoWater.level.app;

import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.domain.WaterTankLevelRepository;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.domain.TankService;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WaterTankLevelAppService extends TankService<WaterTankLevel> {
    private final WaterTankLevelRepository waterTankLevelRepository;

    public WaterTankLevelAppService(TankRepository tankRepository, UserRepository userRepository, WaterTankLevelRepository waterTankLevelRepository) {
        super(tankRepository, userRepository);
        this.waterTankLevelRepository = waterTankLevelRepository;
    }

    public WaterTankLevel save(WaterTankLevel waterTankLevel, String email) {
        validateAndAssignUser(waterTankLevel, email, entity -> {
            waterTankLevel.setTank(entity.getTank());
        },"Level");
        waterTankLevel.setDateMeasurement(Timestamp.valueOf(LocalDateTime.now()));
        return waterTankLevelRepository.save(waterTankLevel);
    }

    public WaterTankLevel findById(Long id) {
        if(!waterTankLevelRepository.existsById(id)) {
            throw new IllegalArgumentException("El tanque con ID " + id + " no existe.");
        }
        return waterTankLevelRepository.findById(id);
    }
    public List<WaterTankLevel> findAll() {
        return waterTankLevelRepository.findAll();
    }

    @Override
    protected TankDTO getTankFromEntity(WaterTankLevel entity) {
        return entity.getTank();
    }
}
