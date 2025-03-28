package com.project.ecoWater.sensor.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SensorDataJpaRep extends JpaRepository<SensorDataEntity, Long> {
}
