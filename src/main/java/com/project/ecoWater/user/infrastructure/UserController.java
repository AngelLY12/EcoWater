package com.project.ecoWater.user.infrastructure;

import com.project.ecoWater.user.app.UserApplicationService;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor()
public class UserController {
    private final UserApplicationService userService;

    @GetMapping("/getUser/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        Optional<User> userOptional= userService.findUserByEmail(email);
        if(userOptional.isEmpty()) {
            System.out.println("Usuario no encontrado: " + email);
            return ResponseEntity.notFound().build();

        }
        System.out.println("Usuario encontrado: " + userOptional.get());
        return ResponseEntity.ok(userOptional.get());

    }

    @PatchMapping("/updateUser/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        user.setEmail(email);
        Optional<User> userOptional= userService.updateUser(user);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        String result = userService.deleteUserByEmail(email);
        if (result.equals("User not found")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
