package com.project.ecoWater.filling.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TankFillingJpaRep extends JpaRepository<TankFillingEntity, Long> {
}
