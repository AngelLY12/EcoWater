package com.project.ecoWater.tank.domain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tank {
    private Long tankId;
    private String tankName;
    private String capacity;
    private String typeFilling;
}
