package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.DTO.IN.MoneyRequest;
import com.example.bankmanagementsystem.DTO.IN.TransferRequest;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccount(@AuthenticationPrincipal User user, @Valid @RequestBody Account account) {
        accountService.createAccount(user.getId(), account);
        return ResponseEntity.status(201).body(new ApiResponse("Account created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer id, @Valid @RequestBody Account account) {
        accountService.updateAccount(user.getId(), id, account);
        return ResponseEntity.status(200).body(new ApiResponse("Account updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccount(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        accountService.deleteAccount(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("Account deleted successfully"));
    }


    //extra endpoints
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<ApiResponse> activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.activateAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity<Account> viewAccountDetails(
            @PathVariable Integer accountId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.viewAccountDetails( user.getId(), accountId));
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<Account>> listUserAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(accountService.listCurrentUserAccounts(user.getId()));
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity<ApiResponse> deposit(
            @PathVariable Integer accountId,
            @Valid @RequestBody MoneyRequest request,
            @AuthenticationPrincipal User user) {
        accountService.deposit(user.getId(), accountId, request.getAmount());
        return ResponseEntity.status(200).body(new ApiResponse("Deposit completed successfully"));
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity<ApiResponse> withdraw(
            @PathVariable Integer accountId,
            @Valid @RequestBody MoneyRequest request,
            @AuthenticationPrincipal User user) {
        accountService.withdraw(user.getId(), accountId, request.getAmount());
        return ResponseEntity.status(200).body(new ApiResponse("Withdrawal completed successfully"));
    }

    @PutMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(
            @Valid @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user) {
        accountService.transfer(user.getId(), request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return ResponseEntity.status(200).body(new ApiResponse("Transfer completed successfully"));
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<ApiResponse> blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }
}
