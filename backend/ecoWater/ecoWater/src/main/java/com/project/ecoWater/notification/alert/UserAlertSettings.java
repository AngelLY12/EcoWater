package com.project.ecoWater.notification.alert;

import com.project.ecoWater.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAlertSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    private AlertType alertType;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "threshold")
    private Float threshold;

    @Column(name = "threshold_unit")
    private String unit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String title;
    private String message;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
