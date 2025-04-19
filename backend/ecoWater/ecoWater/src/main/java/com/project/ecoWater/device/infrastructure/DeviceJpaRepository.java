package com.project.ecoWater.device.infrastructure;

import com.project.ecoWater.tank.infrastructure.TankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceJpaRepository extends JpaRepository<DeviceEntity, String> {

    @Query("SELECT d FROM DeviceEntity d WHERE d.user.email = :email")
    List<DeviceEntity> findAllByUser(@Param("email")String email);

    @Modifying
    @Query("DELETE FROM DeviceEntity d WHERE d.deviceId = :deviceId AND d.user.email = :email")
    void deleteByIdAndUser(@Param("deviceId") String deviceId,@Param("email") String email);


}
