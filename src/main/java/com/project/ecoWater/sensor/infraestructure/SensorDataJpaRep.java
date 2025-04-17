package com.project.ecoWater.sensor.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SensorDataJpaRep extends JpaRepository<SensorDataEntity, Long> {
    @Query("SELECT sd.distance FROM SensorDataEntity sd " +
            "JOIN TankEntity t ON sd.tank.tankId = t.tankId " +
            "JOIN TankFillingEntity tf ON t.tankId = tf.tank.tankId " +
            "WHERE tf.fillingId = :fillingId " +
            "ORDER BY sd.measurementTime DESC LIMIT 1")
    float findDistanceByTankFillingId(@Param("fillingId") Long fillingId);
}
