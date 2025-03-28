package com.project.ecoWater.tank.domain;

import java.util.List;
import java.util.Optional;

public interface TankRepository {
    Optional<Tank> findById(Long tankId);
    List<Tank> findAll();
    Tank save(Tank tank);
    void delete(Tank tank);
    boolean existsById(Long tankId);
    Optional<Tank> updateTank(Tank tank);

}
