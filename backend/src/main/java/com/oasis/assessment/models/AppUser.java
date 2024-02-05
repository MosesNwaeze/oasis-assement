package com.oasis.assessment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oasis.assessment.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@JsonIgnoreProperties(value = {"tasks"})
public class AppUser {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Email field cannot be empty")
    @Size(max = 255, min = 1,message = "email cannot be less than 1 and greater than 255 characters")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Email field cannot be empty")
    @Size(max = 255, min = 1,message = "email cannot be less than 1 and greater than 255 characters")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password field cannot be empty")
    @Size(max = 255, min = 8,message = "Password cannot be less than 8 and greater than 255 characters")
    private String password;

    @NotBlank(message = "First name field cannot be empty")
    @Size(max = 255, min = 1,message = "First name cannot be less than 1 and greater than 255 characters")
    private String firstName;

    @NotBlank(message = "Last name field cannot be empty")
    @Size(max = 255, min = 1,message = "Last name cannot be less than 1 and greater than 255 characters")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status field cannot be empty")
    private StatusEnum status;

    @OneToMany(mappedBy = "appUserModel",cascade = CascadeType.ALL)
    private List<Task> tasks;

    private String roles;

}
