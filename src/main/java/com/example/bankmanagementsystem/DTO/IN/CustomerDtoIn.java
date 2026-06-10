package com.example.bankmanagementsystem.DTO.IN;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoIn {

    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Username length must be between 4 and 10 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password length must be at least 6 characters")
    private String password;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name length must be between 2 and 20 characters")
    private String name;

    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "Role must be CUSTOMER, EMPLOYEE, or ADMIN only")
    private String role;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d+$", message = "Phone number must start with 05")
    private String phoneNumber;
}
