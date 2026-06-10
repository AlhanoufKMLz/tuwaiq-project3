package com.example.bankmanagementsystem.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/register/customer").permitAll()
                        .requestMatchers("/api/v1/auth/register/employee", "/api/v1/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/employees/**", "/api/v1/accounts/activate/**", "/api/v1/accounts/block/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                        .requestMatchers("/api/v1/accounts/**", "/api/v1/customers/**").hasAnyAuthority("CUSTOMER", "EMPLOYEE", "ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
