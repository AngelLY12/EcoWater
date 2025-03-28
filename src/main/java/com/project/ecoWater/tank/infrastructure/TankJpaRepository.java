package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TankJpaRepository extends JpaRepository<TankEntity, Long> {
}
