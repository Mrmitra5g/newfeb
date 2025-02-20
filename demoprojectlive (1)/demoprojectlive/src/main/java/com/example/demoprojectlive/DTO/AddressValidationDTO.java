package com.example.demoprojectlive.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressValidationDTO {

    @NotBlank(message = "Organization ID is required")
    private String orgId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Street address is required")
    @Size(min = 1, message = "Street address must have at least 1 character")
    private String street;

    // Getters and Setters

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}

