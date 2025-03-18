package com.project.ecoWater.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User saveUser(User user);
    Optional<User> findUserByEmail(String email);
    List<User> findAllUsers();
    void deleteUserByEmail(String email);
    boolean existUserByEmail(String email);
    Optional<User> updateUser(User user);

}
