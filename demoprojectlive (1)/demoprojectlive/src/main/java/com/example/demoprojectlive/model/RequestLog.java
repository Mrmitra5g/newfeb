package com.example.demoprojectlive.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RequestLog {

    @Id
    @GeneratedValue
    private Long id;

    private String orgId;
    private String userId;
    private String search;
    private LocalDateTime timestamp;

    // Constructor
    public RequestLog(String orgId, String userId, String search, LocalDateTime timestamp) {
        this.orgId = orgId;
        this.userId = userId;
        this.search = search;
        this.timestamp = timestamp;
    }

    public RequestLog() {
        this.timestamp = LocalDateTime.now();
    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
