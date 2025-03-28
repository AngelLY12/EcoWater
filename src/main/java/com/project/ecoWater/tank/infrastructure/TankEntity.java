package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.FillingType;
import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    private BigDecimal capacity;


    @Enumerated(EnumType.STRING)
    @Column(
            name = "filling_type",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private FillingType fillingType;
    @Column
    private BigDecimal tankHeight;
    private Timestamp createdAt;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
