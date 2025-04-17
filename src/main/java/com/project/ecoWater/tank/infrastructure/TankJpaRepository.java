package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TankJpaRepository extends JpaRepository<TankEntity, Long> {
    @Query("SELECT t FROM TankEntity t " +
            "JOIN TankFillingEntity tf ON t.tankId = tf.tank.tankId " +
            "WHERE tf.fillingId = :fillingId")
    TankEntity findByTankFillingId(@Param("fillingId") Long fillingId);

    @Query("SELECT t FROM TankEntity t WHERE t.user.email = :email")
    List<TankEntity> findAllByUser(@Param("email")String email);
}
