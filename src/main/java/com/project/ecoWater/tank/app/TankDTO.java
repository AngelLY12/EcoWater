package com.project.ecoWater.tank.app;

import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TankDTO {
    private Long tankId;
    private UserDTO user;
}
