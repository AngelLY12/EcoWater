package com.project.ecoWater.notification;

import com.project.ecoWater.notification.alert.AlertType;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
        @Index(columnList = "createdAt"),
        @Index(columnList = "enabled")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;
    private String title;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
