package com.example.demoprojectlive.repository;




import com.example.demoprojectlive.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByOrgSfid(String orgSfid);

    Organization findByOrgSfid(String orgSfid);
    List<Organization> findAll();  // Optional, as JpaRepository already has a findAll() method

    List<Organization> findAllByDeleted(Boolean deleted);

    boolean existsByOrgIdAndIsDeletedFalse(Long orgId);

//    boolean existsByOrgIdAndIsDeletedFalse(String orgId);
}
