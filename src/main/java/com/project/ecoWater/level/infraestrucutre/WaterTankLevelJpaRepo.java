package com.project.ecoWater.level.infraestrucutre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterTankLevelJpaRepo extends JpaRepository<WaterTankLevelEntity, Long> {
}
