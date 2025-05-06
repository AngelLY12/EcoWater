package com.project.ecoWater.user.infrastructure;

import com.project.ecoWater.user.app.UserDTO;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;


    @Override
    public User saveUser(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity savedUserEntity = userJpaRepository.save(userEntity);
        return UserMapper.toDomain(savedUserEntity);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userJpaRepository.findById(userId).map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAllUsers() {
        return userJpaRepository.findAll().stream().map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserByEmail(String email) {
        userJpaRepository.deleteByEmail(email);

    }

    @Override
    public boolean existUserByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    @Override
    public Optional<User> updateUser(User user, String email) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity updatedUserEntity = userJpaRepository.save(userEntity);
        return Optional.ofNullable(UserMapper.toDomain(updatedUserEntity));
    }

    @Override
    public void updateTokenFMC(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userJpaRepository.save(userEntity);
    }

}
