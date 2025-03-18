package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.TankFilling;
import com.project.ecoWater.tank.domain.TankFillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TankFillingRepositoryImpl implements TankFillingRepository {


    private final TankJpaFillingRepository tankJpaFillingRepository;

    @Override
    public TankFilling save(TankFilling tankFilling) {
        TankFillingEntity tankFillingEntity = TankMapper.tankFillingToTankFillingEntity(tankFilling);
        TankFillingEntity savedEntity = tankJpaFillingRepository.save(tankFillingEntity);
        return TankMapper.tankFillingEntityToTankFilling(savedEntity);
    }

    @Override
    public Optional<TankFilling> findById(Long TankFillingid) {
        return Optional.empty();
    }

    @Override
    public List<TankFilling> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long TankFillingid) {
        return false;
    }
}
