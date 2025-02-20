package com.example.demoprojectlive.DTO;

import com.example.demoprojectlive.model.Organization;
import com.example.demoprojectlive.model.OrganizationHistory;
import com.example.demoprojectlive.model.OrganizationService;
import com.example.demoprojectlive.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OrganizationDTO {

    private Long orgId;
    private String orgSfid;
    private String companyName;
    private String companyContact;
    private String alternateContact;
    private String phone;
    private Boolean isDeleted;
    private String email;

    private Date createdAt;  // Will hold the actual Date (created_at)

    private Date updatedAt;  // Will hold the actual Date (updated_at)

    private LocalDateTime deletedAt;  // Will hold the actual LocalDateTime (deleted_at)

    private LocalDateTime createdOn;  // Will hold the actual LocalDateTime (created_on)

    private LocalDateTime updatedOn;  // Will hold the actual LocalDateTime (updated_on)

    private LocalDateTime deletedOn;  // Will hold the actual LocalDateTime (deleted_on)

    private boolean present;
    private boolean deleted;


    // Define One-to-Many relationship with OrganizationHistory
    private List<OrganizationHistory> organizationHistories;

    // Define One-to-Many relationship with OrganizationService
    private List<OrganizationService> organizationServices;

    // Define One-to-Many relationship with User
    private List<User> users;


    public Organization setOrganizationEntity() {
        Organization organization = new Organization();
        organization.setOrgId(this.getOrgId());
        organization.setOrgSfid(this.getOrgSfid());
        organization.setCompanyName(this.getCompanyName());
        organization.setCompanyContact(this.companyContact);
        organization.setAlternateContact(this.alternateContact);
        organization.setPhone(this.phone);
        organization.setIsDeleted(this.isDeleted);
        organization.setEmail(this.getEmail());
        organization.setCreatedAt(new Date());
//        organization.setUpdatedAt(this.getUpdatedAt());
//        organization.setDeletedAt(this.getDeletedAt());
        organization.setCreatedOn(LocalDateTime.now());
//        organization.setUpdatedOn(this.getUpdatedOn());
        organization.setPresent(this.present);
        organization.setDeleted(this.isDeleted());
        organization.setOrganizationHistories(this.getOrganizationHistories());
        organization.setOrganizationServices(this.getOrganizationServices());
        organization.setUsers(this.getUsers());


        return organization;
    }

    public Organization updateOrganizationEntity(Organization organization) {
        organization.setOrgId(this.getOrgId());
        organization.setOrgSfid(this.getOrgSfid());
        organization.setCompanyName(this.getCompanyName());
        organization.setCompanyContact(this.getCompanyContact());
        organization.setAlternateContact(this.getAlternateContact());
        organization.setPhone(this.getPhone());
        organization.setIsDeleted(this.isDeleted());
        organization.setEmail(this.getEmail());
//        organization.setCreatedAt(this.getCreatedAt());
//        organization.setUpdatedAt(this.getUpdatedAt());
//        organization.setDeletedAt(this.getDeletedAt());
//        organization.setCreatedOn(this.getCreatedOn());
//        organization.setUpdatedOn(this.getUpdatedOn());
        organization.setDeleted(this.isDeleted());
        organization.setPresent(this.isPresent());
        organization.setDeleted(this.isDeleted());
        organization.setOrganizationHistories(this.getOrganizationHistories());
        organization.setOrganizationServices(this.getOrganizationServices());
        organization.setUsers(this.getUsers());

        return organization;
    }

//    public Organization deleteOrganizationEntity(Organization organization) {
//        organization.setDeleted(true);  // Mark the organization as deleted
//        organization.setDeletedAt(LocalDateTime.now());  // Set the deleted timestamp
//        organization.setDeletedOn(LocalDateTime.now()); // Set the deletedOn timestamp
//        return organization;
//    }

    public Organization getOrganizationEntity() {
        Organization organization = new Organization();
        organization.setOrgId(this.getOrgId());
        organization.setOrgSfid(this.getOrgSfid());
        organization.setCompanyName(this.getCompanyName());
        organization.setCompanyContact(this.getCompanyContact());
        organization.setAlternateContact(this.getAlternateContact());
        organization.setPhone(this.getPhone());
        organization.setIsDeleted(this.isDeleted());
        organization.setEmail(this.getEmail());
        organization.setCreatedAt(this.getCreatedAt());
        organization.setUpdatedAt(this.getUpdatedAt());
        organization.setDeletedAt(this.getDeletedAt());
        organization.setCreatedOn(this.getCreatedOn());
        organization.setUpdatedOn(this.getUpdatedOn());
        organization.setDeleted(this.isDeleted());
        organization.setPresent(this.isPresent());

        organization.setOrganizationHistories(this.getOrganizationHistories());
        organization.setOrganizationServices(this.getOrganizationServices());
        organization.setUsers(this.getUsers());
        return organization;
    }


}
