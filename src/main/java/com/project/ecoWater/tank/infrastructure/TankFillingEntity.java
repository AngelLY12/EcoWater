package com.project.ecoWater.tank.infrastructure;



import com.project.ecoWater.device.domain.Device;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "water_consumptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankFillingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tankFillingId;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne
    @JoinColumn(name = "tank_id", nullable = false)
    private TankEntity tank;

    @Column(name = "total_volume")
    private float totalVolume;

    @Column(name = "started_date")
    private Timestamp startDate;

    @Column(name = "ended_date")
    private Timestamp endDate;

}
