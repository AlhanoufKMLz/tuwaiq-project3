package com.example.bankmanagementsystem.DTO.IN;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull(message = "From account id cannot be null")
    private Integer fromAccountId;

    @NotNull(message = "To account id cannot be null")
    private Integer toAccountId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;
}
