package com.example.demoprojectlive.DTO;

import lombok.Data;

@Data
public class ErrorResponseDTO {
    private String status;
    private String message;

    public ErrorResponseDTO(String error, String organizationWithOrgSfidNotFound) {
    }

    // Getters and Setters
}
