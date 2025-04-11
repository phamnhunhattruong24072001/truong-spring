package com.truong_java.spring.repository;

import com.truong_java.spring.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    Optional<UserEntity> findOneByEmailAndPassword(String email, String password);

    UserEntity findByUsername(String username);
}
