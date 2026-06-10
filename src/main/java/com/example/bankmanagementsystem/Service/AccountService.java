package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository userRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void createAccount(Integer customerId, Account account) {
        Customer customer = checkCustomer(customerId);
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new ApiException("Account number already exists");
        }
        account.setIsActive(false);
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void updateAccount(Integer userId, Integer id, Account updatedAccount) {
        Account account = checkAccount(id);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        account.setAccountNumber(updatedAccount.getAccountNumber());
        account.setBalance(updatedAccount.getBalance());
        account.setIsActive(updatedAccount.getIsActive());
        accountRepository.save(account);
    }

    public void deleteAccount(Integer userId, Integer id) {
        Account account = checkAccount(id);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        accountRepository.delete(account);
    }


    //extra endpoints
    public void activateAccount(Integer userId, Integer accountId) {
        Account account = checkAccount(accountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        account.setIsActive(true);
        accountRepository.save(account);
    }

    public Account viewAccountDetails(Integer userId, Integer accountId) {
        Account account = checkAccount(accountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        return account;
    }

    public List<Account> listCurrentUserAccounts(Integer userId) {
        User user = checkUser(userId);
        Customer customer = user.getCustomer();

        if (customer == null) {
            throw new ApiException("Current user is not a customer");
        }
        return accountRepository.findAccountsByCustomer(customer);
    }

    public void deposit(Integer userId, Integer accountId, BigDecimal amount) {
        Account account = checkAccount(accountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        ensureActive(account);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    public void withdraw(Integer userId, Integer accountId, BigDecimal amount) {
        Account account = checkAccount(accountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        ensureActive(account);
        ensureEnoughBalance(account, amount);
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    public void transfer(Integer userId, Integer fromAccountId, Integer toAccountId, BigDecimal amount) {
        Account fromAccount = checkAccount(fromAccountId);
        Account toAccount = checkAccount(toAccountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(fromAccount, user);

        ensureActive(fromAccount);
        ensureActive(toAccount);
        ensureEnoughBalance(fromAccount, amount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public void blockAccount(Integer userId, Integer accountId) {
        Account account = checkAccount(accountId);
        User user = checkUser(userId);

        authorizeAccountOwnerOrStaff(account, user);

        account.setIsActive(false);
        accountRepository.save(account);
    }


    //helper methods
    private Account checkAccount(Integer id) {
        Account account = accountRepository.findAccountById(id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        return account;
    }

    private Customer checkCustomer(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        return customer;
    }

    private User checkUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        return user;
    }

    private void ensureActive(Account account) {
        if (!Boolean.TRUE.equals(account.getIsActive())) {
            throw new ApiException("Account is not active");
        }
    }

    private void ensureEnoughBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance");
        }
    }

    private void authorizeAccountOwnerOrStaff(Account account, User user) {
        String role = user.getRole();
        if (role.equals("ADMIN") || role.equals("EMPLOYEE")) {
            return;
        }
        Customer customer = user.getCustomer();
        if (customer == null || !account.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("You are not authorized to access this account");
        }
    }
}
