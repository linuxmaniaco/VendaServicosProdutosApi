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

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column (name = "created_by")
    private String createdBy;

    @Column (name = "updated_by")
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = this.dateCreated;
        this.createdBy = UUID.randomUUID().toString();
        this.updatedBy = UUID.randomUUID().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
        this.updatedBy = UUID.randomUUID().toString();
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}

//package com.VendaServicosProdutosApi.VendaServicosProdutosApi.model;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@AllArgsConstructor@NoArgsConstructor
//@Getter
//@MappedSuperclass
//public class DomainBase {
//    private LocalDateTime dateCreated = LocalDateTime.now();
//    private LocalDateTime lastUpdated = LocalDateTime.now();
//    private String createdBy = "system";
//    private String updatedBy = "system";
//}
