package com.project.ecoWater.tank.app;

import com.project.ecoWater.tank.domain.Tank;
import com.project.ecoWater.tank.domain.TankRepository;
import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.user.infrastructure.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TankApplicationService {
    private final TankRepository tankRepository;
    private final UserRepository userRepository;

    public Tank findTankById(Long tankId) {
        if (!tankRepository.existsById(tankId)) {
            throw new IllegalArgumentException("No such tank id");
        }
        return tankRepository.findById(tankId).get();
    }

    public List<Tank> findAllTanks(String email) {
        List<Tank> allTanks = tankRepository.findAll(email);
        if(allTanks.isEmpty()){
            throw new IllegalArgumentException("No tanks found for this user.");
        }
        return allTanks;
    }

    @Transactional
    public Tank saveTank(Tank tank, String username) {
        User user= userRepository.findUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("No user found"));
        UserDTO userTank= UserMapper.userToUserDTO(user);
        tank.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tank.setUser(userTank);
        return tankRepository.save(tank);
    }

    @Transactional
    public void deleteTank(Long tankId, String email) {
        if (!tankRepository.existsById(tankId)) {
            throw new IllegalArgumentException("No such tank id");
        }
        tankRepository.delete(tankId, email);
    }

    @Transactional
    public Optional<Tank> updateTank(Tank tank, String email) {
        User user= userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found"));
        UserDTO userTank= UserMapper.userToUserDTO(user);
        tank.setUser(userTank);
        Optional<Tank> optionalTank = tankRepository.findById(tank.getTankId());
        if (optionalTank.isPresent()) {
            Tank updatedTank = optionalTank.get();
            if (tank.getTankName() != null) {
                updatedTank.setTankName(tank.getTankName());
            }
            if (tank.getCapacity() != null) {
                updatedTank.setCapacity(tank.getCapacity());
            }
            if (tank.getFillingType() != null) {
                updatedTank.setFillingType(tank.getFillingType());
            }
            if(tank.getTankHeight() != null) {
                updatedTank.setTankHeight(tank.getTankHeight());
            }
            Tank savedTank = tankRepository.save(updatedTank);
            return Optional.of(savedTank);
        }
        return Optional.empty();
    }
}
