package com.project.ecoWater.user.infrastructure;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u WHERE u.email=:email")
    Optional<UserEntity> findByEmail(@Param("email") String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.email = :email")
    void deleteByEmail(@Param("email")String email);
}
