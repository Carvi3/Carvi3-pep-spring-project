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

    public AccountService(){} //This shouldn't be called

    @Autowired
    public AccountService(AccountRepository accountRepository){ //Is this supposed to be used in my Controller class?
        this.accountRepository = accountRepository;
    }

    public boolean checkUsername(String username){
        return username.length() != 0;
    }
    public boolean checkPassword(String password){
        return password.length() >= 4;
    }


    public Account register(Account account) throws CredentialsInvalidException, DuplicateUsernameException{
        if(!checkUsername(account.getUsername()) || !checkPassword(account.getPassword())){
            throw new CredentialsInvalidException();
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if(optionalAccount.isPresent()){
           throw new DuplicateUsernameException(); 
        }
        
        /*if(accountRepo.findByUsername(account.getUsername()) != null){
            throw new DuplicateUsernameException();
        }*/
        return accountRepository.save(account);
    }

    public Account login(Account account) throws CredentialsInvalidException{
        if(!checkUsername(account.getUsername()) || !checkPassword(account.getPassword())){
            throw new CredentialsInvalidException();
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());

        if(optionalAccount.isPresent()){
            Account dbEntry = optionalAccount.get();
            if(account.getPassword().equals(dbEntry.getPassword())){
                return dbEntry;
            }
        }
        throw new CredentialsInvalidException();
    }

    public AccountRepository getAccountRepo(){
        return this.accountRepository;
    }
}
