package com.project.ecoWater.device.domain;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id", columnDefinition = "uuid")
    private UUID deviceId;

    @Column(name="user_id")
    private UUID userId;

    @Column(name = "device_name")
    private String deviceName;

    @ManyToOne
    @JoinColumn(name = "device_type_id", nullable = false)
    private DeviceType deviceType;

    private String deviceLocation;
    private Timestamp creationRegister;
}
