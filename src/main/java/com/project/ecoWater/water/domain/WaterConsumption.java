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
public class WaterConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waterConsumptionId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "type_use")
    private String typeUse;

    @Column(name = "flow_rate_consumption")
    private float flowRateConsumption;

    @Column(name = "total_volume")
    private float totalVolume;

    @Column(name = "started_date")
    private Timestamp startDate;

    @Column(name = "ended_date")
    private Timestamp endDate;

}
