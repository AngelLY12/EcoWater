package com.project.ecoWater.user.infrastructure;

import com.project.ecoWater.auth.infrastructure.AuthRequest;
import com.project.ecoWater.auth.infrastructure.RegisterRequest;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
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
                .userId(userEntity.getUserId())
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

    public static UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userName(user.getUser_name())
                .build();
    }

    public static UserDTO userEntityToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .userName(userEntity.getName())
                .build();
    }

    public static UserEntity userDTOToUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .userId(userDTO.getUserId())
                .email(userDTO.getEmail())
                .name(userDTO.getUserName())
                .build();
    }

}
