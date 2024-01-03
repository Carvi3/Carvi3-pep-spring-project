package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.repository.AccountRepository;


@Service
public class AccountService {
    
    
    private AccountRepository accountRepository;

    public AccountService(){} //This shouldn't be called <-- Not needed

    @Autowired
    public AccountService(AccountRepository accountRepository){ //Good Practice
        this.accountRepository = accountRepository;
    }

    public boolean checkUsername(String username){
        return username.length() != 0;
    }
    public boolean checkPassword(String password){
        return password.length() >= 4;
    }


    public Account register(Account account) throws NotSuccessfulException, DuplicateUsernameException{
        if(!checkUsername(account.getUsername()) || !checkPassword(account.getPassword())){
            throw new NotSuccessfulException("Credentials do not meet Minimum");
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if(optionalAccount.isPresent()){
           throw new DuplicateUsernameException("Duplicate Username Found"); 
        }
        
        /*if(accountRepo.findByUsername(account.getUsername()) != null){
            throw new DuplicateUsernameException();
        }*/
        return accountRepository.save(account);
    }

    public Account login(Account account) throws CredentialsInvalidException, NotSuccessfulException{
        if(!checkUsername(account.getUsername()) || !checkPassword(account.getPassword())){
            throw new CredentialsInvalidException("Credentials do not meet Minimum");
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());

        if(optionalAccount.isPresent()){
            Account dbEntry = optionalAccount.get(); 
            if(account.getPassword().equals(dbEntry.getPassword())){
                return dbEntry;
            }
        }
        throw new CredentialsInvalidException("Login Unsuccessful");
    }

    public AccountRepository getAccountRepo(){
        return this.accountRepository;
    }
}
