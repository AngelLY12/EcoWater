package com.project.ecoWater.user.app;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID userId;
    private String email;
    private String userName;
    private String tokenFMC;
}
