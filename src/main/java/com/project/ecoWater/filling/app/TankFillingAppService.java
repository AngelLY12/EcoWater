package com.project.ecoWater.filling.app;


import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.filling.domain.TankFillingRepository;
import com.project.ecoWater.sensor.domain.SensorDataRepository;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.tank.domain.TankService;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.project.ecoWater.tank.domain.Tank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TankFillingAppService extends TankService<TankFilling> {
    private final TankFillingRepository tankFillingRepository;
    private final TankRepository tankRepository;
    private final SensorDataRepository sensorDataRepository;

    public TankFillingAppService(TankRepository tankRepository, UserRepository userRepository,
                                 TankFillingRepository tankFillingRepository,
                                 SensorDataRepository sensorDataRepository) {
        super(tankRepository, userRepository);
        this.tankFillingRepository = tankFillingRepository;
        this.tankRepository = tankRepository;
        this.sensorDataRepository=sensorDataRepository;
    }


    @Transactional
    public TankFilling save(TankFilling tankFilling, String email) {
        validateAndAssignUser(tankFilling, email, entity -> {
            tankFilling.setTank(entity.getTank());
        },"tank");
        tankFilling.setStartedDate(Timestamp.valueOf(LocalDateTime.now()));
        return tankFillingRepository.save(tankFilling);
    }

    public TankFilling findById(Long id) {
        if(!tankFillingRepository.existsById(id)) {
            throw new IllegalArgumentException("El llenado con ID " + id + " no existe.");
        }
        return tankFillingRepository.findById(id);
    }
    public List<TankFilling> findAll() {
        return tankFillingRepository.findAll();
    }

    @Override
    protected TankDTO getTankFromEntity(TankFilling entity) {
        return entity.getTank();
    }


    @Transactional
    public TankFilling confirmTankFull(Long tankFillingId, String email) {
        TankFilling tankFilling = tankFillingRepository.findById(tankFillingId);
        System.out.println("TANK FILLING: "+tankFilling);
        if(tankFilling == null) {
            throw new IllegalArgumentException("El llenado con ID " + tankFillingId + " no fue encontrado");
        }
        validateAndAssignUser(tankFilling,email,entity->{
            tankFilling.setTank(entity.getTank());
        },"tank");
        tankFilling.setFinishedDate(Timestamp.valueOf(LocalDateTime.now()));
        return tankFillingRepository.save(tankFilling);

    }
}
