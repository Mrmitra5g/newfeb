package com.example.demoprojectlive.DTO;

import lombok.Data;

@Data
public class ServiceRequestDTO {
    private String serviceName;
    private int maxLookups;
    private boolean isDeleted;

    // Getters and Setters
}
