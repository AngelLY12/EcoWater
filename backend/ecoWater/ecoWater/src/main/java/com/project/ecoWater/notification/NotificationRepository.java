package com.project.ecoWater.notification;


import com.google.api.gax.paging.Page;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime dateTime);

    @Query("SELECT n FROM NotificationEntity n WHERE n.user.email = :email ORDER BY n.createdAt DESC")
    List<NotificationEntity> findByUserEmail(@Param("email") String email); // Renombra a findByUserEmail

    // Añade paginación
    @Query("SELECT n FROM NotificationEntity n WHERE n.user.email = :email ORDER BY n.createdAt DESC")
    Page<NotificationEntity> findByUserEmail(@Param("email") String email, Pageable pageable);
}
