package com.project.ecoWater.user.app;

import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        if(userRepository.existUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }
        return userRepository.saveUser(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> findUserByEmail(String email) {
        userRepository.existUserByEmail(email);
        return Optional.ofNullable(userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User exists in check but not found in fetch.")));
    }
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public String deleteUserByEmail(String email) {
        if(userRepository.existUserByEmail(email)) {
            userRepository.deleteUserByEmail(email);
            return "User deleted successfully";
        }
        return "User not found";
    }

    @Transactional

    public Optional<User> updateUser(User updateUser, String email) {
        Optional<User> userEntityOptional = userRepository.findUserByEmail(email);
        if(userEntityOptional.isPresent()) {
            User user = userEntityOptional.get();
            if(updateUser.getUser_name() != null) {
                user.setUser_name(updateUser.getUser_name());
            }
            if(updateUser.getLast_name() != null) {
                user.setLast_name(updateUser.getLast_name());
            }
            if(updateUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            }
            if(updateUser.getAge()!=null){
                user.setAge(updateUser.getAge());
            }
            User savedUser = userRepository.saveUser(user);
            return Optional.of(savedUser);
        }
        return Optional.empty();
    }

    public boolean existUserByEmail(String email) {
        if(!userRepository.existUserByEmail(email)){
            throw new IllegalArgumentException("User with email " + email + " does not exist.");

        }
        return true;

    }

    @Transactional
    public void updateFcmToken(String token, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (user.getTokenFMC() != null && user.getTokenFMC().equals(token)) {
            throw new IllegalArgumentException("Token ya estaba actualizado");
        }

        user.setTokenFMC(token);
        userRepository.updateTokenFMC(user);
    }

}
