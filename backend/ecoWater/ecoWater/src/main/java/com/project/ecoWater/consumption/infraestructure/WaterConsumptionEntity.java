package com.project.ecoWater.consumption.infraestructure;


import com.project.ecoWater.device.app.DeviceDTO;
import com.project.ecoWater.device.infrastructure.DeviceEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "water_consumptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterConsumptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_consumption_id", updatable = false, nullable = false)
    private Long consumptionId;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = true)
    @JoinColumn(name = "device_id",  nullable = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private DeviceEntity device;

    @Column
    private Float flowRate;
    private Float totalConsumption;
    private Timestamp startedDate;
}
