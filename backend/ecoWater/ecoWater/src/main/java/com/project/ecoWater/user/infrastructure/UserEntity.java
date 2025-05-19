package com.project.ecoWater.user.infrastructure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ecoWater.device.infrastructure.DeviceEntity;
import com.project.ecoWater.notification.NotificationEntity;
import com.project.ecoWater.notification.alert.UserAlertSettings;

import com.project.ecoWater.sensor.infraestructure.SensorDataEntity;
import com.project.ecoWater.tank.infrastructure.TankEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name="created_at")
    private Timestamp created;
    private String tokenFMC;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<DeviceEntity> devices;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TankEntity> tanks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<NotificationEntity> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserAlertSettings> alerts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
}