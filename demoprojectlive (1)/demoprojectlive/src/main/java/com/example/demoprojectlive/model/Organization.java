package com.example.demoprojectlive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Organization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;
    @NotBlank(message = "org_sfid must not be empty")
    @Column(unique = true)
    private String orgSfid;
    @NotBlank(message = "company_name must not be empty")
    private String companyName;
    private String companyContact;
    private String alternateContact;
    private String phone;
    private Boolean isDeleted;
    @Email(message = "Invalid email address")
    private String email;

//   private Date createdAt;  // Will hold the actual Date (created_at)
////
////    @Temporal(TemporalType.TIMESTAMP)
// private Date updatedAt;  // Will hold the actual Date (updated_at)
////
////    @Temporal(TemporalType.TIMESTAMP)
//  private LocalDateTime deletedAt;  // Will hold the actual LocalDateTime (deleted_at)
////
////    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime createdOn;  // Will hold the actual LocalDateTime (created_on)
////
////    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime updatedOn;  // Will hold the actual LocalDateTime (updated_on)
////
////    @Temporal(TemporalType.TIMESTAMP)
//   private LocalDateTime deletedOn;  // Will hold the actual LocalDateTime (deleted_on)*/

    private boolean present;
    private boolean deleted;


    // Define One-to-Many relationship with OrganizationHistory
    @OneToMany(mappedBy = "organization")
    private List<OrganizationHistory> organizationHistories;

    // Define One-to-Many relationship with OrganizationService
    @OneToMany(mappedBy = "organization")
    private List<OrganizationService> organizationServices;

    // Define One-to-Many relationship with User
    @OneToMany(mappedBy = "organization")
    private List<User> users;
}
