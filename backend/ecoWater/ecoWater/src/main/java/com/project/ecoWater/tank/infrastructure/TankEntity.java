package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.device.infrastructure.DeviceEntity;
import com.project.ecoWater.filling.infraestructure.TankFillingEntity;
import com.project.ecoWater.level.infraestrucutre.WaterTankLevelEntity;
import com.project.ecoWater.sensor.domain.SensorData;
import com.project.ecoWater.sensor.infraestructure.SensorDataEntity;
import com.project.ecoWater.tank.domain.FillingType;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tanks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tank_id", updatable = false, nullable = false)
    private Long tankId;

    @Column
    private String tankName;
    @Column
    private Float capacity;


    @Enumerated(EnumType.STRING)
    @Column(
            name = "filling_type",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private FillingType fillingType;
    @Column
    private Float tankHeight;
    private Timestamp createdAt;
    @Column(nullable = true)
    private Boolean isMain;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "tank")
    private List<WaterTankLevelEntity> tanksLevels;

    @OneToMany(mappedBy = "tank")
    private List<TankFillingEntity> tankFillings;

    @OneToMany(mappedBy = "tank")
    private List<DeviceEntity> devices;

}
