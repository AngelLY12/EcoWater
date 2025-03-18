package com.project.ecoWater.tank.infrastructure;

import com.project.ecoWater.tank.domain.Tank;
import jakarta.persistence.*;
import lombok.*;

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
    private String capacity;
    @Column
    private String typeFilling;
}
