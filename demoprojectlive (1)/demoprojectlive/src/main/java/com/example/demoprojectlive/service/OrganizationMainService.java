package com.example.demoprojectlive.service;

import com.example.demoprojectlive.DTO.OrganizationDTO;
import com.example.demoprojectlive.DTO.OrganizationRequestDTO;
import com.example.demoprojectlive.DTO.OrganizationResponseDTO;
import com.example.demoprojectlive.model.Organization;

import java.util.List;

public interface OrganizationMainService {
/*
    // Add Organization
    Organization addOrganization(Organization organization) throws OrganizationAlreadyExistsException;

    // Update Organization
    Organization updateOrganization(String orgSfid, Organization updatedOrganization) throws OrganizationNotFoundException;

    Organization getOrganizationByOrgSfid(String orgSfid) throws OrganizationNotFoundException;

    boolean deleteOrganization(String orgSfid);*/

    // Add Organization
    Organization addOrganization(OrganizationDTO organizationDTO) throws OrganizationAlreadyExistsException;

    // Update Organization
    Organization updateOrganization(String orgSfid, OrganizationDTO updatedOrganization) throws OrganizationNotFoundException;

    // Get Organization by org_sfid
    Organization getOrganizationByOrgSfid(String orgSfid) throws OrganizationNotFoundException;

    // Delete Organization
    boolean deleteOrganization(String orgSfid);

    // Get all organizations
    List<OrganizationDTO> getAllOrganizations();


    OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTODTO) throws OrganizationAlreadyExistsException;


    OrganizationResponseDTO updateOrganization(String orgSfid, OrganizationRequestDTO organizationRequestDTO) throws OrganizationNotFoundException;
}
