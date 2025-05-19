package com.project.ecoWater.consumption.infraestructure;

import com.project.ecoWater.level.infraestrucutre.WaterTankLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterConsumptionJpaRep extends JpaRepository<WaterConsumptionEntity, Long> {
    @Query("SELECT (SUM(c.flowRate), c.device.deviceLocation) FROM WaterConsumptionEntity c WHERE c.device.user.email = :email GROUP BY c.device.deviceLocation")
    List<WaterConsumptionEntity> findConsumptionByLocation(@Param("email") String email);

    @Query("SELECT c FROM WaterConsumptionEntity c where c.device.user.email = :email")
    List<WaterConsumptionEntity> findConsumptionByEmail(@Param("email") String email);

    @Query("SELECT t FROM WaterConsumptionEntity t WHERE t.device.tank.user.email = :email AND t.device.tank.isMain= true ORDER BY t.startedDate DESC limit 1")
    Optional<WaterConsumptionEntity> findTopLevelOfMainTankByEmail(@Param("email")String email);

    @Query("SELECT t FROM WaterConsumptionEntity t WHERE t.device.tank.isMain = true AND t.device.tank.user.email = :email ORDER BY t.startedDate DESC")
    List<WaterConsumptionEntity> findAllMainTankLevelsByUser(@Param("email") String email);

    @Query("SELECT t FROM WaterConsumptionEntity t WHERE t.device.tank.isMain = true AND t.device.tank.user.email = :email ORDER BY t.startedDate ASC LIMIT 1")
    Optional<WaterConsumptionEntity> findFirstMeasurementForMainTank(@Param("email") String email);
    @Query("SELECT t FROM WaterConsumptionEntity t WHERE t.device.tank.isMain = true AND t.device.tank.user.email = :email AND DATE(t.startedDate) = :date ORDER BY t.startedDate DESC")
    List<WaterConsumptionEntity> findMainTankLevelsByUserAndDate(@Param("email") String email, @Param("date") LocalDate date);

    @Query("SELECT t FROM WaterConsumptionEntity t WHERE t.device.tank.isMain = true AND t.device.tank.user.email = :email " +
            "AND t.startedDate BETWEEN :startDateTime AND :endDateTime ORDER BY t.startedDate DESC")
    List<WaterConsumptionEntity> findMainTankLevelsByUserAndDateTime(@Param("email") String email,
                                                                   @Param("startDateTime") LocalDateTime startDateTime,
                                                                   @Param("endDateTime") LocalDateTime endDateTime);
}
