package com.project.ecoWater.device.infrastructure;


import com.project.ecoWater.device.domain.DeviceType;
import com.project.ecoWater.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceEntity {

    @Id
    @Column(name = "device_id", updatable = false, nullable = false)
    private String deviceId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "device_name")
    private String deviceName;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "device_type",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private DeviceType deviceType;

    @Column(name = "device_location")
    private String deviceLocation;

    @Column(name = "creation_register")
    private Timestamp creationRegister;
    private Boolean connected;
    private Timestamp lastSeen;
    private String ssid;

}