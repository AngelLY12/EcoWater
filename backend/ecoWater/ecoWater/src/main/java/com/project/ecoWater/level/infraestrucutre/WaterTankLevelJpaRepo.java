package com.project.ecoWater.level.infraestrucutre;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterTankLevelJpaRepo extends JpaRepository<WaterTankLevelEntity, Long> {
    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.user.email = :email AND FUNCTION('DATE', t.dateMeasurement) = CURRENT_DATE ")
    List<WaterTankLevelEntity> findAllByUser(@Param("email")String email);

    @Query(value = "SELECT ((t.tankHeight - sd.distance)/t.tankHeight)*t.capacity FROM SensorDataEntity sd JOIN TankEntity t ON t.tankId = sd.device.tank.tankId WHERE sd.device.tank.tankId = :tankId ORDER BY sd.measurementTime DESC LIMIT 1")
    float findLatestWaterLevelByTankId(@Param("tankId") Long tankId);

    @Query(value = "SELECT ((t.tankHeight - sd.distance) / t.tankHeight)*100 FROM SensorDataEntity sd JOIN TankEntity t ON t.tankId = sd.device.tank.tankId WHERE sd.device.tank.tankId = :tankId ORDER BY sd.measurementTime DESC LIMIT 1")
    float findLastFillPercentageByTankId(@Param("tankId")Long tankId);

    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.user.email = :email AND t.tank.isMain= true ORDER BY t.dateMeasurement DESC limit 1")
    Optional<WaterTankLevelEntity> findTopLevelOfMainTankByEmail(@Param("email")String email);

    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.isMain = true AND t.tank.user.email = :email ORDER BY t.dateMeasurement DESC")
    List<WaterTankLevelEntity> findAllMainTankLevelsByUser(@Param("email") String email);

    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.isMain = true AND t.tank.user.email = :email ORDER BY t.dateMeasurement ASC LIMIT 1")
    Optional<WaterTankLevelEntity> findFirstMeasurementForMainTank(@Param("email") String email);
    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.isMain = true AND t.tank.user.email = :email AND DATE(t.dateMeasurement) = :date ORDER BY t.dateMeasurement DESC")
    List<WaterTankLevelEntity> findMainTankLevelsByUserAndDate(@Param("email") String email, @Param("date") LocalDate date);

    @Query("SELECT t FROM WaterTankLevelEntity t WHERE t.tank.isMain = true AND t.tank.user.email = :email " +
            "AND t.dateMeasurement BETWEEN :startDateTime AND :endDateTime ORDER BY t.dateMeasurement DESC")
    List<WaterTankLevelEntity> findMainTankLevelsByUserAndDateTime(@Param("email") String email,
                                                                   @Param("startDateTime") LocalDateTime startDateTime,
                                                                   @Param("endDateTime") LocalDateTime endDateTime);


}
