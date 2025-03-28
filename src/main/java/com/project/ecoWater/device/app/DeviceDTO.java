package com.project.ecoWater.device.app;

import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeviceDTO {
    private UUID deviceId;
    private UserDTO user;

}
