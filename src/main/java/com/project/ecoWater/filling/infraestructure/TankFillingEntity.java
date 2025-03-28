package com.project.ecoWater.filling.infraestructure;

import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.infrastructure.TankEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tank_fillings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankFillingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tank_filling_id", updatable = false, nullable = false)
    private Long fillingId;

    @Column
    private BigDecimal totalVolume;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "tank_id")
    private TankEntity tank;
}
