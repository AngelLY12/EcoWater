package com.project.ecoWater.tank.app;

import com.project.ecoWater.tank.domain.TankFilling;
import com.project.ecoWater.tank.domain.TankFillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TankFillingAppService {


    private final TankFillingRepository tankFillingRepository;

    public TankFilling saveTankFilling(TankFilling tankFilling) {
        if(tankFillingRepository.existsById(tankFilling.getTankFillingId())){
            throw new IllegalArgumentException("Invalid tank filling id");
        }
        return tankFillingRepository.save(tankFilling);
    }
    public TankFilling getTankFillingById(Long tankFillingId) {
        if(!tankFillingRepository.existsById(tankFillingId)){
            throw new IllegalArgumentException("No such tank filling id");
        }
        return tankFillingRepository.findById(tankFillingId).get();
    }

    public List<TankFilling> getAllTankFillings() {
        return tankFillingRepository.findAll();
    }

    public boolean existsTankFillingById(Long tankFillingId) {
        return tankFillingRepository.existsById(tankFillingId);
    }
}
