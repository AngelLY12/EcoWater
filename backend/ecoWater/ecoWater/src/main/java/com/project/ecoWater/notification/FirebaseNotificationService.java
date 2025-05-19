package com.project.ecoWater.notification;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.project.ecoWater.notification.alert.*;
import com.project.ecoWater.tank.infrastructure.TankMapper;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserEntity;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirebaseNotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FirebaseApp firebaseApp;
    private final UserAlertSettingsService userAlertSettingsService;



    public void sendNotification(String email, String title, String body, AlertType alertType) {
        try {
            System.out.println("=== Iniciando envío de notificación ===");
            System.out.println("Destinatario: " + email);
            if (firebaseApp == null) {
                throw new IllegalStateException("FirebaseApp no está inicializado");
            }
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
            UserDTO userDTO = UserMapper.userToUserDTO(user);

            String fcmToken = userDTO.getTokenFMC();

            System.out.println("Token FCM del usuario: " + fcmToken); // 👈 Log crítico


            if (fcmToken == null) {
                throw new RuntimeException("Usuario no tiene token FCM registrado");
            }

            List<AlertDTO> alertSettings = userAlertSettingsService.getAllUserAlertSettings(email);

            boolean isAlertEnabled = alertSettings.stream()
                    .anyMatch(setting -> setting.getAlertType() == alertType && setting.isEnabled());

            if (!isAlertEnabled) {
                System.out.println("ℹ️ Alerta " + alertType + " deshabilitada para el usuario " + email + ". No se envía notificación.");
                return;
            }

            NotificationEntity notification = NotificationEntity.builder()
                    .title(title)
                    .message(body)
                    .user(UserMapper.userDTOToUserEntity(userDTO))
                    .alertType(alertType)
                    .build();

            notificationRepository.save(notification);

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .putData("title", title)
                    .putData("message", body)
                    .putData("alert_type", alertType.getValue())
                    .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                    .build();
            System.out.println("Enviando mensaje FCM..."); // 👈
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notificación enviada con éxito. Respuesta FCM: " + response); // 👈
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ Error FCM: " + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
        }
        }

        public List<NotificationDTO> getNotifications(String email){
        List<NotificationEntity> notifications = notificationRepository.findByUserEmail(email);
        if(notifications.isEmpty()){
            throw new IllegalArgumentException("Notification not found");
        }
        return notifications.stream().map(NotificationMapper::mapToDto).collect(Collectors.toList());
        }




}
