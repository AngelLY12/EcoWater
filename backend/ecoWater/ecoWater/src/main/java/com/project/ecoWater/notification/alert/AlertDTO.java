package com.project.ecoWater.notification.alert;


import com.project.ecoWater.user.app.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDTO {
    private Long id;
    private String title;
    private String message;
    private AlertType alertType;
    private boolean enabled;
    private Float threshold;
    private String unit;
    private LocalDateTime createdAt;
    private boolean wasSent;
    private UserDTO user;
}
