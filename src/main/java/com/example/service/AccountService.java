package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepo;

    public boolean checkUsername(String username){
        return username.length() != 0;
    }
    public boolean checkPassword(String password){
        return password.length() >= 4;
    }

    public Account register(Account account){
        if(checkUsername(account.getUsername()) && checkPassword(account.getPassword())){
            return accountRepo.save(account);
        }
        return null;
    }

    //public Account Login(Account acount){

    //}
}
