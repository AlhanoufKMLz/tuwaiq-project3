package com.example.bankmanagementsystem.DTO.IN;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDtoIn {

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

    @NotNull(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Salary must be non-negative")
    private BigDecimal salary;
}
