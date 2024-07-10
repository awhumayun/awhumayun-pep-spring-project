package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Account registerUser(Account account) throws Exception {
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account loginUser(String username, String password) throws Exception {
        Account account = accountRepository.findByUsername(username);

        if (account == null || !account.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return account;
    }
}
