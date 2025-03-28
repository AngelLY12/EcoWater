package com.project.ecoWater.device.domain;


import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class Device {


    private UUID deviceId;

    private UserDTO user;

    private String deviceName;

    private DeviceType deviceType;

    private String deviceLocation;

    private Timestamp creationRegister;
}
