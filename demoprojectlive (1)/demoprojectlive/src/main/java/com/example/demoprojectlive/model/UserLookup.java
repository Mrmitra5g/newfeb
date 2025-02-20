package com.example.demoprojectlive.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class UserLookup {

    @Id
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime lookupTimestamp;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLookupTimestamp() {
        return lookupTimestamp;
    }

    public void setLookupTimestamp(LocalDateTime lookupTimestamp) {
        this.lookupTimestamp = lookupTimestamp;
    }
}

