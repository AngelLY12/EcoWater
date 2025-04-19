package com.project.ecoWater.tank.domain;


import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class Tank {
    private Long tankId;
    private String tankName;
    private Float capacity;
    private FillingType fillingType;
    private Float tankHeight;
    private Timestamp createdAt;
    private Boolean isMain;
    private UserDTO user;

}
