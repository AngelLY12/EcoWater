package com.project.ecoWater;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public abstract class BaseService<T, R> {
    protected final R repository;
    protected final UserRepository userRepository;

    protected void validateAndAssignUser(T entity, String email, Consumer<T> setEntity, String entityType) {
        Object entityObj = getEntityFromGeneric(entity);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        UserDTO userDTO = UserMapper.userToUserDTO(user);
        if (entityObj == null || !existsInRepository(entityObj)) {
            throw new IllegalArgumentException("El " + entityType + " con ID no existe o no es válido.");
        }
        setUserToEntity(entityObj, userDTO);
        setEntity.accept(entity);
    }

    protected abstract Object getEntityFromGeneric(T entity);
    protected abstract boolean existsInRepository(Object entity);
    protected abstract void setUserToEntity(Object entity, UserDTO userDTO);
}
