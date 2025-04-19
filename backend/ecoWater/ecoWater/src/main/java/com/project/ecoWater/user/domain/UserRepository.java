package com.project.ecoWater.user.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User saveUser(User user);
    Optional<User> findUserByEmail(String email);
    Optional<User> findById(UUID userId);

    List<User> findAllUsers();
    void deleteUserByEmail(String email);
    boolean existUserByEmail(String email);
    Optional<User> updateUser(User user, String email);

}
