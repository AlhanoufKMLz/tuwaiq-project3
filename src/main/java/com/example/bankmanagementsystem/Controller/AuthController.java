package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.DTO.IN.CustomerDtoIn;
import com.example.bankmanagementsystem.DTO.IN.EmployeeDtoIn;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<ApiResponse> registerCustomer(@Valid @RequestBody CustomerDtoIn customer) {
        authService.registerCustomer(customer);
        return ResponseEntity.status(201).body(new ApiResponse("Customer registered successfully"));
    }

    @PostMapping("/register/employee")
    public ResponseEntity<ApiResponse> registerEmployee(@Valid @RequestBody EmployeeDtoIn employee) {
        authService.registerEmployee(employee);
        return ResponseEntity.status(201).body(new ApiResponse("Employee registered successfully"));
    }
}
