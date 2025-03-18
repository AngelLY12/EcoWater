package com.project.ecoWater.tank.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TankJpaFillingRepository extends JpaRepository<TankFillingEntity, Long> {
}
