package com.project.ecoWater.notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificacionCleanupTask {
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupOldNotifications() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusWeeks(1);
        notificationRepository.deleteByCreatedAtBefore(fechaLimite);
        System.out.println("Notificaciones antiguas eliminadas");
    }
}
