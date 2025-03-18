package com.project.ecoWater.device.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "device_types")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_type_id")
    private int deviceTypeId;

    @Column(name = "type_name")
    private String deviceTypeName;


}
