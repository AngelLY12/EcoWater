package com.project.ecoWater.notification.alert;

import com.project.ecoWater.user.infrastructure.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAlertSettingsRepository extends JpaRepository<UserAlertSettings, Long> {

    @Query("SELECT s FROM UserAlertSettings s Where s.user.email = :email AND s.id= :id")
    Optional<UserAlertSettings> findByEmailAndId(@Param("email") String email,@Param("id") Long id);

    @Query("SELECT s FROM UserAlertSettings s WHERE s.user.email = :email")
    List<UserAlertSettings> findByUser(@Param("email") String email);


    @Query("SELECT s FROM UserAlertSettings s WHERE s.user.email = :email AND s.alertType= :alertType")
    List<UserAlertSettings> findAllByUserAndAlertType(@Param("email") String email,@Param("alertType") AlertType alertType);


}