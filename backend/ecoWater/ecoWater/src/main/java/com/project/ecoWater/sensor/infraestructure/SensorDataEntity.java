package com.project.ecoWater.sensor.infraestructure;

import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.infrastructure.DeviceEntity;
import com.project.ecoWater.tank.infrastructure.TankEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(cascade = CascadeType.REFRESH, optional = true)
    @JoinColumn(name = "device_id",  nullable = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private DeviceEntity device;

    @Column
    private Float distance;
    private Timestamp measurementTime;
}
