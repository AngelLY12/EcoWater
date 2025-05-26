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

    @Query("SELECT c FROM WaterConsumptionEntity c where c.device.user.email = :email AND FUNCTION('DATE', c.startedDate) = CURRENT_DATE")
    List<WaterConsumptionEntity> findConsumptionByEmail(@Param("email") String email);

    @Query("SELECT c FROM WaterConsumptionEntity c WHERE c.device.user.email = :email AND c.startedDate BETWEEN :startDate AND :endDate")
    List<WaterConsumptionEntity> findConsumptionByEmailAndDate(@Param("email") String email, @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT (SUM(c.totalConsumption)) FROM WaterConsumptionEntity c WHERE c.device.user.email = :email AND c.startedDate BETWEEN :start AND :end")
    Double findByUserEmailAndStartedDateBetween(
           @Param("email") String email,
           @Param("start") LocalDateTime start,
           @Param("end") LocalDateTime end
    );


}
