package com.VendaServicosProdutosApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class DomainBase {

    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column (name = "created_by", updatable = false)
    private String createdBy;

    @Column (name = "updated_by")
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = this.dateCreated;
        this.createdBy = getCurrentUser();
        this.updatedBy = getCurrentUser();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
        this.updatedBy = getCurrentUser();
    }

    private String getCurrentUser() {
        // futuramente pode vir do Spring Security
        return "SYSTEM";
    }

//    @PrePersist
//    protected void onCreate() {
//        this.dateCreated = LocalDateTime.now();
//        this.lastUpdated = this.dateCreated;
//        this.createdBy = UUID.randomUUID().toString();
//        this.updatedBy = UUID.randomUUID().toString();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.lastUpdated = LocalDateTime.now();
//        this.updatedBy = UUID.randomUUID().toString();
//    }
//
//    public void setDateCreated(LocalDateTime dateCreated) {
//        this.dateCreated = dateCreated;
//    }
//
//    public void setLastUpdated(LocalDateTime lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
//
//    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}
