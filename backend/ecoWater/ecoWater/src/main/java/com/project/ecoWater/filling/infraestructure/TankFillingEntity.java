package com.project.ecoWater.filling.infraestructure;

import com.project.ecoWater.tank.app.TankDTO;
import com.project.ecoWater.tank.infrastructure.TankEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Float totalVolume;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = true)
    @JoinColumn(name = "tank_id",  nullable = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private TankEntity tank;
}
