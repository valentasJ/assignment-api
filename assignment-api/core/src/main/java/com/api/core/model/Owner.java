package com.api.core.model;

import com.api.core.config.SensitiveView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Pattern;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Owner name is required")
    @JsonView(SensitiveView.Public.class)  // Public view for all roles
    private String name;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$", message = "Invalid Social Security Number format")
    @JsonView(SensitiveView.Admin.class)  // Admin view, sensitive data for Admin only
    private String socialSecurityNumber;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonBackReference
    @JsonView(SensitiveView.Public.class)
    private Company company;
}
