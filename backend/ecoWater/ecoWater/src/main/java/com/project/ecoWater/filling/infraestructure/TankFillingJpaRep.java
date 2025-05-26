package com.project.ecoWater.filling.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TankFillingJpaRep extends JpaRepository<TankFillingEntity, Long> {
    @Query("SELECT f FROM TankFillingEntity f WHERE f.tank.isMain = true AND f.tank.user.email = :email AND DATE(f.finishedDate) = :date ORDER BY f.finishedDate DESC")
    List<TankFillingEntity> findMainTankFillingsByUserAndDate(@Param("email") String email, @Param("date") LocalDate date);

    @Query("SELECT f FROM TankFillingEntity f WHERE f.tank.isMain = true AND f.tank.user.email = :email " +
            "AND f.finishedDate BETWEEN :startDateTime AND :endDateTime ORDER BY f.finishedDate DESC")
    List<TankFillingEntity> findMainTankFillingsByUserAndDateTime(@Param("email") String email,
                                                                  @Param("startDateTime") LocalDateTime startDateTime,
                                                                  @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT f FROM TankFillingEntity f WHERE f.tank.user.email = :email AND f.startedDate = :startedDate")
    List<TankFillingEntity> findTankFillingByEmailAndDate(@Param("email")String email,@Param("startedDate") LocalDateTime startedDate);

    @Query("SELECT f FROM TankFillingEntity f WHERE f.tank.user.email = :email AND  FUNCTION('DATE', f.startedDate) = CURRENT_DATE")
    List<TankFillingEntity> findTankFillingByEmailAndCurrent(@Param("email")String email);
}
