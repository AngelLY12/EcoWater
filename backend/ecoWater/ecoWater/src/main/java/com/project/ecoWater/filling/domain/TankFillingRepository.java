package com.project.ecoWater.filling.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TankFillingRepository {
    TankFilling save(TankFilling tankFilling);
    TankFilling findById(Long id);
    List<TankFilling> findAll();
    boolean existsById(Long id);
    List<TankFilling> findMainTankFillingsByUserAndDate(String email, LocalDate date);
    List<TankFilling> findMainTankFillingsByUserAndDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<TankFilling> findByEmailAndDate(String email, LocalDateTime startedDate);
    List<TankFilling> findByEmailAndCurrent(String email);


}
