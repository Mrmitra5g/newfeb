package com.example.demoprojectlive.DTO;

import lombok.Data;

@Data
public class OrganizationRequestDTO {
    private String orgSfid;
    private String companyName;
    private String companyContact;
    private String alternateContact;
    private String phone;
    private String email;
    private boolean isDeleted;
    private ServiceRequestDTO service;

    // Getters and Setters
}
