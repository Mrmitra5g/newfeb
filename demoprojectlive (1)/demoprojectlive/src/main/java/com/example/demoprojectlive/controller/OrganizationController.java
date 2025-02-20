package com.example.demoprojectlive.controller;

import com.example.demoprojectlive.DTO.*;
import com.example.demoprojectlive.model.Organization;
import com.example.demoprojectlive.repository.OrganizationRepository;
import com.example.demoprojectlive.service.NotFoundException;
import com.example.demoprojectlive.service.OrganizationAlreadyExistsException;
import com.example.demoprojectlive.service.OrganizationMainService;
import com.example.demoprojectlive.service.OrganizationNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationMainService organizationMainService;
    @Autowired
    private OrganizationRepository organizationRepository;

  /*  // Add Organization (POST)
    @PostMapping
    public ResponseEntity<?> addOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            Organization createdOrganization = organizationMainService.addOrganization(organizationDTO);
            return new ResponseEntity<>(new ApiResponse("success", "Organization added successfully", createdOrganization), HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ApiResponse(e.getStatusCode().toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "Internal server error. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
  // Add Organization (POST)
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
    @PostMapping
    public ResponseEntity<?> addOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            Organization createdOrganization = organizationMainService.addOrganization(organizationDTO);
            return new ResponseEntity<>(new ApiResponse("success", "Organization added successfully", createdOrganization), HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                errorMessages.append(violation.getMessage()).append("; ");
            }
            return new ResponseEntity<>(new ApiResponse("error", errorMessages.toString()), HttpStatus.BAD_REQUEST);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ApiResponse(e.getStatusCode().toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error occurred while adding organization", e); // Log the exception with stack trace

            return new ResponseEntity<>(new ApiResponse("error", "Internal server error. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Update Organization (PUT)
    @PutMapping("/{org_sfid}")
    public ResponseEntity<?> updateOrganization(@PathVariable String org_sfid, @RequestBody OrganizationDTO updatedOrganization) {
        try {
            Organization existingOrganization = organizationMainService.updateOrganization(org_sfid, updatedOrganization);
            return new ResponseEntity<>(new ApiResponse("success", "Organization updated successfully", existingOrganization), HttpStatus.OK);
        } catch (OrganizationNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("error", "Organization with org_sfid not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "Unable to process the request. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get Organization by org_sfid (GET)
    @GetMapping("/{org_sfid}")
    public ResponseEntity<?> getOrganizationByOrgSfid(@PathVariable String org_sfid) {
        try {
            Organization organization = organizationMainService.getOrganizationByOrgSfid(org_sfid);
            return new ResponseEntity<>(new ApiResponse("success", "Organization retrieved successfully", organization), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "Internal server error. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Organization (DELETE)
    @DeleteMapping("/{org_sfid}")
    public ResponseEntity<?> deleteOrganization(@PathVariable String org_sfid) {
        try {
            boolean isDeleted = organizationMainService.deleteOrganization(org_sfid);
            if (isDeleted) {
                return new ResponseEntity<>(new ApiResponse("success", "Organization deleted successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse("error", "Organization with org_sfid not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "Unable to process the request. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all organizations (GET)
    @GetMapping
    public ResponseEntity<?> getAllOrganizations() {
        try {
            List<OrganizationDTO> organizations = organizationMainService.getAllOrganizations();
            return new ResponseEntity<>(new ApiResponse("success", "Organizations retrieved successfully", organizations), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("error", "Internal server error. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to soft delete by ID
    @DeleteMapping("/organization/{orgId}")
    public ResponseEntity<String> softDeleteOrganizationById(@PathVariable Long orgId) {
        Organization organization = organizationRepository.findById(orgId).orElse(null);

        if (organization == null) {
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

        // Mark the organization as deleted (without saving to DB)
        organization.setDeleted(true);

        // Don't save it to the database (simulating the soft delete without persisting)
        // Commented out line: organizationRepository.save(organization);

        // Return a response indicating success
        return new ResponseEntity<>("Organization marked as deleted", HttpStatus.OK);
    }




    // Endpoint to soft delete by OrgSfid
    @DeleteMapping("/orgId/{orgSfid}")
    public ResponseEntity<String> softDeleteOrganizationByOrgSfid(@PathVariable String orgSfid) {
        Organization organization = organizationRepository.findByOrgSfid(orgSfid);

        if (organization == null) {
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

        // Set the isDeleted flag to true (soft delete)
        organization.setDeleted(true);
        organizationRepository.save(organization);

        return new ResponseEntity<>("Organization marked as deleted", HttpStatus.OK);
    }



//    3rd api



   /* @PostMapping("/neworganization")
    public ResponseEntity<Object> createOrganization(@RequestBody OrganizationRequestDTO organizationRequestDTO) {
        try {
            // Service logic to add organization
            OrganizationResponseDTO response = organizationMainService.createOrganization(organizationRequestDTO);
            return new ResponseEntity<>(new SuccessResponseDTO("success", "Organization added successfully", response), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponseDTO("error", "Validation error: Missing or invalid fields"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponseDTO("error", "Internal server error. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{org_sfid}")
    public ResponseEntity<Object> updateOrganization(@PathVariable String orgSfid, @RequestBody OrganizationRequestDTO organizationRequestDTO) {
        try {
            // Service logic to update organization
            OrganizationResponseDTO response = organizationMainService.updateOrganization(orgSfid, organizationRequestDTO);
            return new ResponseEntity<>(new SuccessResponseDTO("success", "Organization updated successfully", response), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new ErrorResponseDTO("error", "Organization with org_sfid not found"), HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponseDTO("error", "Validation error: Invalid fields"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponseDTO("error", "Unable to process the request. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
