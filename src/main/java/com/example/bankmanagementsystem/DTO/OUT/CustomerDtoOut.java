package com.example.bankmanagementsystem.DTO.OUT;

import com.example.bankmanagementsystem.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoOut {

    private User user;

    private String phoneNumber;
}
