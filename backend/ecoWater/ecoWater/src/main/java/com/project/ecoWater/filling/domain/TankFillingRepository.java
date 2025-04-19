package com.project.ecoWater.filling.domain;

import java.util.List;

public interface TankFillingRepository {
    TankFilling save(TankFilling tankFilling);
    TankFilling findById(Long id);
    List<TankFilling> findAll();
    boolean existsById(Long id);

}
