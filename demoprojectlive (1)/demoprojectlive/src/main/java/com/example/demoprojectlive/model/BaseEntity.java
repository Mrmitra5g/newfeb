package com.example.demoprojectlive.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {

    //    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at")
    private Date createdAt;  // Will hold the actual Date (created_at)

    @Column
    public Date updatedAt;  // Will hold the actual Date (updated_at)

    @Column
    public LocalDateTime deletedAt;  // Will hold the actual LocalDateTime (deleted_at)

    //    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_on")
    public LocalDateTime createdOn;  // Will hold the actual LocalDateTime (created_on)

    @Column
    public LocalDateTime updatedOn;  // Will hold the actual LocalDateTime (updated_on)

    @Column
    public LocalDateTime deletedOn;
}
