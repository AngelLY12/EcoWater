package com.project.ecoWater.tank.domain;


import com.project.ecoWater.device.domain.Device;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
public class TankFilling {

    private Long tankFillingId;

    private Device device;

    private Tank tank;

    private float totalVolume;

    private Timestamp startDate;

    private Timestamp endDate;

}
