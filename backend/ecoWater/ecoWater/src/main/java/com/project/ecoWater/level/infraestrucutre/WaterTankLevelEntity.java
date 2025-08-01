package com.project.ecoWater.level.infraestrucutre;

import com.project.ecoWater.tank.infrastructure.TankEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Entity
@Table(name = "water_tanks_level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterTankLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_tank_level_id", updatable = false, nullable = false)
    private Long levelId;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = true)
    @JoinColumn(name = "tank_id",  nullable = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private TankEntity tank;

    @Column
    private Float waterLevel;
    private Timestamp dateMeasurement;
    private Float fillPercentage;
}
