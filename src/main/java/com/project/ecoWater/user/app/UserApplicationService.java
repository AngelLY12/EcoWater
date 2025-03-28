package com.project.ecoWater.user.app;

import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
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
        return userRepository.findUserByEmail(email);
    }
    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public String deleteUserByEmail(String email) {
        if(userRepository.existUserByEmail(email)) {
            userRepository.deleteUserByEmail(email);
            return "User deleted successfully";
        }
        return "User not found";
    }

    public Optional<User> updateUser(User updateUser) {
        Optional<User> userEntityOptional = userRepository.findUserByEmail(updateUser.getEmail());
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
        return userRepository.existUserByEmail(email);
    }

}
