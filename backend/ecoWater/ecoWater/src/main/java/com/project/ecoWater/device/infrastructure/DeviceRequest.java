package com.project.ecoWater.device.infrastructure;

import com.project.ecoWater.device.domain.DeviceType;
import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.domain.Tank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceRequest

{
    String device_id;
    String device_name;
    DeviceType device_type;
    String device_location;
    String ssid;
    TankDTO tank;
}
