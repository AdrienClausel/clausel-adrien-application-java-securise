package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    public User(String username, String password, String fullname, String role){
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
