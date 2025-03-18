package com.project.ecoWater.tank.domain;

import java.util.List;
import java.util.Optional;

public interface TankFillingRepository {

    TankFilling save(TankFilling tankFilling);
    Optional<TankFilling> findById(Long TankFillingid);
    List<TankFilling> findAll();
    boolean existsById(Long TankFillingid);
}
