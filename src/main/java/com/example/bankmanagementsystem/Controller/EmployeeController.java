package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@AuthenticationPrincipal User user, @Valid @RequestBody Employee employee) {
        employeeService.updateEmployee(user.getId(), employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteEmployee(@AuthenticationPrincipal User user) {
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }
}
