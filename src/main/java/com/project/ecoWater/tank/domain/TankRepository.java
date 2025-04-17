package com.project.ecoWater.tank.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TankRepository {
    Optional<Tank> findById(Long tankId);
    List<Tank> findAll(String email);
    Tank save(Tank tank);
    void delete(Tank tank);
    boolean existsById(Long tankId);
    Optional<Tank> updateTank(Tank tank, String email);

    Tank findByTankFillingId(Long fillingId);

}
