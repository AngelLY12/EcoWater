package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class TankRepositoryImpl implements TankRepository {
    private final TankJpaRepository tankJpaRepository;
    @Override
    public Optional<Tank> findById(Long tankId) {
        return tankJpaRepository.findById(tankId).map(TankMapper::tankEntityToTank);

    }

    @Override
    public List<Tank> findAll(String email) {
        return tankJpaRepository.findAllByUser(email).stream().map(TankMapper::tankEntityToTank).collect(Collectors.toList());
    }

    @Override
    public Tank save(Tank tank) {
        TankEntity tankEntity = TankMapper.tankToTankEntity(tank);
        TankEntity savedEntity = tankJpaRepository.save(tankEntity);
        return TankMapper.tankEntityToTank(savedEntity);
    }

    @Override
    public void delete(Tank tank) {
        tankJpaRepository.deleteById(tank.getTankId());
    }

    @Override
    public boolean existsById(Long tankId) {
        return tankJpaRepository.existsById(tankId);
    }

    @Override
    public Optional<Tank> updateTank(Tank tank, String email) {
        TankEntity tankEntity = TankMapper.tankToTankEntity(tank);
        TankEntity updatedTankEntity = tankJpaRepository.save(tankEntity);
        return Optional.ofNullable(TankMapper.tankEntityToTank(updatedTankEntity));
    }


    @Override
    @Transactional
    public Tank findByTankFillingId(Long fillingId) {
        TankEntity tankEntity=tankJpaRepository.findByTankFillingId(fillingId);
        return TankMapper.tankEntityToTank(tankEntity);

    }
}
