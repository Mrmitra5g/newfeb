package com.example.demoprojectlive.repository;



import com.example.demoprojectlive.model.UserLookup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserLookupRepository extends JpaRepository<UserLookup, Long> {
    // Custom query to find lookups for the user today
    List<UserLookup> findByUserUserIdAndLookupTimestampAfter(Long userId, LocalDateTime date);
}

