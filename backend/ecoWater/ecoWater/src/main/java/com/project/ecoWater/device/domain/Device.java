package com.project.ecoWater.device.domain;


import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.infrastructure.TankEntity;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class Device {


    private String deviceId;

    private UserDTO user;

    private TankDTO tank;

    private String deviceName;

    private DeviceType deviceType;

    private String deviceLocation;

    private Timestamp creationRegister;

    private Boolean connected;

    private Timestamp lastSeen;

    private String ssid;
}
