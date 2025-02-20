package com.example.demoprojectlive.repository;


import com.example.demoprojectlive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository1 extends JpaRepository<User, Long> {
    // Custom query methods
    Optional<User> findByUserIdAndIsDeletedFalse(Long userId);

    boolean existsByUserIdAndIsDeletedFalse(Long userId);
}
