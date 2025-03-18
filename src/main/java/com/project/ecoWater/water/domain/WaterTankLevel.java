package com.project.ecoWater.water.domain;


import com.project.ecoWater.device.domain.Device;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "water_consumption")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterTankLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waterTankLevelId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "water_level")
    private float waterLevel;

    @Column(name = "dataMeasurement")
    private Timestamp dateMeasurement;
}
