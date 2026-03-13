package com.VendaServicosProdutosApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;

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

//    private String getCurrentUser() {
//        // futuramente pode vir do Spring Security
//        return "SYSTEM";
//    }

    private String getCurrentUser() {

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "SYSTEM";
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getEmail(); // ou user.getLogin() ou user.getId().toString()
        }

        return principal.toString();
    }
}
