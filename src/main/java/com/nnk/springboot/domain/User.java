package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;

    @NotBlank(message = "Username is mandatory")
    @Size(max=125)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(max=125)
    private String password;

    @NotBlank(message = "FullName is mandatory")
    @Size(max=125)
    private String fullname;

    @NotBlank(message = "Role is mandatory")
    @Size(max=125)
    private String role;
}
