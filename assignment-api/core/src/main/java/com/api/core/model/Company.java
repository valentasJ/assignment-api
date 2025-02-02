package com.api.core.model;

import com.api.core.config.SensitiveView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SensitiveView.Public.class) // Exposed to all users
    private Long id;

    @NotBlank(message = "Company name is required")
    @JsonView(SensitiveView.Public.class) // Exposed to all users
    private String name;

    @NotBlank(message = "Country is required")
    @JsonView(SensitiveView.Public.class) // Exposed to all users
    private String country;

    @Pattern(regexp = "^\\+?[0-9\\-\\(\\)\\ ]{7,15}$", message = "Invalid phone number format")
    @JsonView(SensitiveView.Admin.class) // Exposed only to admins
    private String phoneNumber;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonView(SensitiveView.Public.class) // Exposed to all users
    private Set<Owner> owners = new HashSet<>();
}
