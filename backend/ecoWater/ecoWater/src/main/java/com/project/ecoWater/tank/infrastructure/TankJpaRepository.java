package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("DELETE FROM TankEntity t WHERE t.tankId = :tankId AND t.user.email = :email")
    void deleteByIdAndUser(@Param("tankId") Long tankId,@Param("email") String email);
}
