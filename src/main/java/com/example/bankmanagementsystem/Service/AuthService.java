package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.DTO.IN.CustomerDtoIn;
import com.example.bankmanagementsystem.DTO.IN.EmployeeDtoIn;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerCustomer(CustomerDtoIn request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole("CUSTOMER");

        Customer customer = new Customer();
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUser(user);

        userRepository.save(user);
        customerRepository.save(customer);
    }

    public void registerEmployee(EmployeeDtoIn request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole("EMPLOYEE");

        Employee employee = new Employee();
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setUser(user);

        userRepository.save(user);
        employeeRepository.save(employee);
    }
}
