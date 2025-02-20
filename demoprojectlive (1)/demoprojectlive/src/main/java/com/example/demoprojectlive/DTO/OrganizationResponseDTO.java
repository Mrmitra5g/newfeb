package com.example.demoprojectlive.DTO;

import lombok.Data;

@Data
public class OrganizationResponseDTO {
    private Long orgId;
    private String orgSfid;
    private String companyName;
    private String companyContact;
    private String alternateContact;
    private String phone;
    private String email;
    private boolean isDeleted;

    // Getters and Setters
}
