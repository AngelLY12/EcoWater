package com.project.ecoWater.user.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Data
@Builder
public class User implements UserDetails {

    private UUID userId;

    private String user_name;

    private String last_name;

    private int age;

    private String email;

    private String password;

    private Timestamp created;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
}
