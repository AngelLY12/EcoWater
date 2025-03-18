package com.project.ecoWater.user.infrastructure;

import com.project.ecoWater.auth.infrastructure.AuthRequest;
import com.project.ecoWater.auth.infrastructure.RegisterRequest;
import com.project.ecoWater.user.domain.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .name(user.getUser_name())
                .lastName(user.getLast_name())
                .age(user.getAge())
                .password(user.getPassword())
                .created(user.getCreated())
                .build();

    }

    public static User toDomain(UserEntity userEntity) {
        return  User.builder()
                .email(userEntity.getEmail())
                .user_name(userEntity.getName())
                .last_name(userEntity.getLastName())
                .age(userEntity.getAge())
                .password(userEntity.getPassword())
                .created(userEntity.getCreated())
                .build();


    }
    public static User registerRequestToUser(RegisterRequest user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .user_name(user.getName())
                .last_name(user.getLastname())
                .age(user.getAge())
                .build();
    }

    public static User authRequestToUser(AuthRequest user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
