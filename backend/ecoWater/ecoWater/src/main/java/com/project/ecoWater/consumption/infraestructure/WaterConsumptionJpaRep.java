package com.project.ecoWater.consumption.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterConsumptionJpaRep extends JpaRepository<WaterConsumptionEntity, Long> {
}
