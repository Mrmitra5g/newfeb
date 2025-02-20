package com.example.demoprojectlive.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OrganizationService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgServiceId;

    @ManyToOne
    @JoinColumn(name = "service_master_id", referencedColumnName = "serviceMasterId")
    private ServiceMaster serviceMaster;

    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "orgId")
    private Organization organization;

    private String serviceName;
    private int maxLookups;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;

    // Getters and Setters

    public Long getOrgServiceId() {
        return orgServiceId;
    }

    public void setOrgServiceId(Long orgServiceId) {
        this.orgServiceId = orgServiceId;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Organization getOrganization(String orgSfid) {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public int getMaxLookups() {
        return maxLookups;
    }

    public void setMaxLookups(int maxLookups) {
        this.maxLookups = maxLookups;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(LocalDateTime deletedOn) {
        this.deletedOn = deletedOn;
    }
}

