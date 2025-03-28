package com.project.ecoWater.filling.domain;


import com.project.ecoWater.tank.app.TankDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TankFilling {
    private Long fillingId;
    private BigDecimal totalVolume;
    private Timestamp startedDate;
    private Timestamp finishedDate;
    private TankDTO tank;
}
