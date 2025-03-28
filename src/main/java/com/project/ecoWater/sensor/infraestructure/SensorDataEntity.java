package com.project.ecoWater.sensor.infraestructure;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.infrastructure.DeviceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "sensor_datas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_data_id", updatable = false, nullable = false)
    private Long sensorId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @Column
    private BigDecimal distance;
    private Timestamp measurementTime;
}
