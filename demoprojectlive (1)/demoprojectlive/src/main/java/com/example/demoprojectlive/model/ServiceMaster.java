package com.example.demoprojectlive.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class ServiceMaster {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private     Long serviceMasterId;


    private String serviceName;


    @OneToMany(mappedBy = "serviceMaster")
    private List<OrganizationService> organizationServices;

    public Long getServiceMasterId() {
        return serviceMasterId;
    }

    public void setServiceMasterId(Long serviceMasterId) {
        this.serviceMasterId = serviceMasterId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<OrganizationService> getOrganizationServices() {
        return organizationServices;
    }

    public void setOrganizationServices(List<OrganizationService> organizationServices) {
        this.organizationServices = organizationServices;
    }
}
