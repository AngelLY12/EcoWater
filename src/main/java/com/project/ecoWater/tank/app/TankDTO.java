package com.project.ecoWater.tank.app;

import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TankDTO {
    private Long tankId;
    private UserDTO user;
    private Float capacity;
    private Float tankHeight;
    private Boolean isMain;
}
