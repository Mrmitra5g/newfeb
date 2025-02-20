package com.example.demoprojectlive.service.implement;

import com.example.demoprojectlive.DTO.OrganizationDTO;
import com.example.demoprojectlive.DTO.OrganizationRequestDTO;
import com.example.demoprojectlive.DTO.OrganizationResponseDTO;
import com.example.demoprojectlive.model.Organization;
import com.example.demoprojectlive.repository.OrganizationRepository;
import com.example.demoprojectlive.service.OrganizationAlreadyExistsException;
import com.example.demoprojectlive.service.OrganizationMainService;
import com.example.demoprojectlive.service.OrganizationNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationMainService {

    @Autowired
    private OrganizationRepository organizationRepository;

    // Add Organization
    @Override
    public Organization addOrganization(OrganizationDTO organizationDTO) {
        // Check if an organization with the same org_sfid already exists
        if (organizationRepository.existsByOrgSfid(organizationDTO.getOrgSfid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization with org_sfid already exists");
        }
        Organization organization = organizationDTO.setOrganizationEntity();
        return organizationRepository.save(organization);
    }

    // Update Organization
 /*   @Override
    public Organization updateOrganization(String orgSfid, OrganizationDTO organizationDTO) throws OrganizationNotFoundException {
        // Fetch the existing organization
        Organization existingOrganization = organizationRepository.findByOrgSfid(orgSfid);
        if (existingOrganization == null) {
            throw new OrganizationNotFoundException("Organization with org_sfid not found");
        }

        // Update the existing organization's fields with the new values
        existingOrganization.setCompanyName(organizationDTO.getCompanyName());
        existingOrganization.setCompanyContact(organizationDTO.getCompanyContact());
        existingOrganization.setAlternateContact(organizationDTO.getAlternateContact());
        existingOrganization.setPhone(organizationDTO.getPhone());
        existingOrganization.setEmail(organizationDTO.getEmail());
        existingOrganization.setDeleted(organizationDTO.isDeleted());

        // Set updated timestamp
        existingOrganization.setUpdatedAt(new Date());
        existingOrganization.setUpdatedOn(LocalDateTime.now());

        return organizationRepository.save(existingOrganization);
    }
*/
    @Override
    public Organization updateOrganization(String orgSfid, OrganizationDTO organizationDTO) throws OrganizationNotFoundException {
        // Fetch the existing organization from the database using orgSfid
        Organization existingOrganization = organizationRepository.findByOrgSfid(orgSfid);

        // Check if the organization exists, if not, throw an exception
        if (existingOrganization == null) {
            throw new OrganizationNotFoundException("Organization with org_sfid not found");
        }

        // Update the existing organization's fields using the method from DTO
        existingOrganization = organizationDTO.updateOrganizationEntity(existingOrganization);

        // Set updated timestamp for tracking when the update happened
        existingOrganization.setUpdatedAt(new Date());
        existingOrganization.setUpdatedOn(LocalDateTime.now());

        // Save the updated organization entity back to the repository
        return organizationRepository.save(existingOrganization);
    }



    // Get Organization by org_sfid
    @Override
    public Organization getOrganizationByOrgSfid(String orgSfid) throws OrganizationNotFoundException {
        // Find the organization by org_sfid
        Optional<Organization> organizationOpt = Optional.ofNullable(organizationRepository.findByOrgSfid(orgSfid));
        if (organizationOpt.isEmpty()) {
            throw new OrganizationNotFoundException("Organization with org_sfid not found");
        }
        return organizationOpt.get();
    }
    @Override
    @Transactional
    public boolean deleteOrganization(String orgSfid) {
        // Find the organization by org_sfid
        Organization existingOrganization = organizationRepository.findByOrgSfid(orgSfid);
        if (existingOrganization == null) {
            return false; // Organization not found
        }

//        // Convert the entity to DTO
//        OrganizationDTO organizationDTO = new OrganizationDTO();
//        organizationDTO.setOrgId(existingOrganization.getOrgId());
//        organizationDTO.setOrgSfid(existingOrganization.getOrgSfid());
//        organizationDTO.setCompanyName(existingOrganization.getCompanyName());
//        organizationDTO.setCompanyContact(existingOrganization.getCompanyContact());
//        organizationDTO.setAlternateContact(existingOrganization.getAlternateContact());
//        organizationDTO.setPhone(existingOrganization.getPhone());
//        organizationDTO.setEmail(existingOrganization.getEmail());
//        organizationDTO.setPresent(existingOrganization.isPresent());
//
//
//        organizationDTO.setOrganizationHistories(existingOrganization.getOrganizationHistories());
//        organizationDTO.setOrganizationServices(existingOrganization.getOrganizationServices());
//        organizationDTO.setUsers(existingOrganization.getUsers());

        existingOrganization.setDeleted(true);  // Mark the organization as deleted
        existingOrganization.setDeletedAt(LocalDateTime.now());  // Set the deleted timestamp
        existingOrganization.setDeletedOn(LocalDateTime.now());

        // Soft delete the organization entity using DTO's method
//        Organization updatedOrganization = organizationDTO.deleteOrganizationEntity(existingOrganization);

        // Save the updated organization entity back to the database
        organizationRepository.save(existingOrganization);

        return true; // Deletion successful
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> allOrganizations = organizationRepository.findAllByDeleted(false);
        List<OrganizationDTO> organizationDTOList = new ArrayList<>();

        // Convert each organization entity to its corresponding DTO
        for (Organization organization : allOrganizations) {
            // Create OrganizationDTO instance
            OrganizationDTO organizationDTO = new OrganizationDTO();

            // Set DTO properties from the Organization entity
            organizationDTO.setOrgId(organization.getOrgId());
            organizationDTO.setOrgSfid(organization.getOrgSfid());
            organizationDTO.setCompanyName(organization.getCompanyName());
            organizationDTO.setCompanyContact(organization.getCompanyContact());
            organizationDTO.setAlternateContact(organization.getAlternateContact());
            organizationDTO.setPhone(organization.getPhone());
            organizationDTO.setEmail(organization.getEmail());
            organizationDTO.setCreatedAt(organization.getCreatedAt());
            organizationDTO.setUpdatedAt(organization.getUpdatedAt());
            organizationDTO.setDeletedAt(organization.getDeletedAt());
            organizationDTO.setCreatedOn(organization.getCreatedOn());
            organizationDTO.setUpdatedOn(organization.getUpdatedOn());
            organizationDTO.setDeleted(organization.getIsDeleted());

            // Set additional fields from the Organization entity
            organizationDTO.setPresent(organization.isPresent());
            organizationDTO.setOrganizationHistories(organization.getOrganizationHistories());
            organizationDTO.setOrganizationServices(organization.getOrganizationServices());
            organizationDTO.setUsers(organization.getUsers());

            // Add the OrganizationDTO to the result list
            organizationDTOList.add(organizationDTO);
        }

        return organizationDTOList;
    }

    @Override
    public OrganizationResponseDTO createOrganization(OrganizationRequestDTO organizationRequestDTODTO) throws OrganizationAlreadyExistsException {
        // Your business logic to create the organization
        OrganizationResponseDTO responseDTO = new OrganizationResponseDTO();

        return responseDTO;
    }

    @Override
    public OrganizationResponseDTO updateOrganization(String orgSfid, OrganizationRequestDTO organizationRequestDTO) throws OrganizationNotFoundException {

        // Your business logic to update the organization
        OrganizationResponseDTO responseDTO = new OrganizationResponseDTO();

        return responseDTO;
    }


}