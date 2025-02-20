package com.example.demoprojectlive.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressDTO {

    @NotBlank(message = "Organization ID is required")
    private String orgId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Search term is required")
    @Size(min = 1, message = "Search term must have at least 3 characters")
    private String search;

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
