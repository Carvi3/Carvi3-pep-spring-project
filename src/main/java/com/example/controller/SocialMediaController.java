package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.*;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    @GetMapping("test/{account_id}")
    public String testMethod(@PathVariable int account_id){
        return "Henlo";
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        //AccountService accountService = app.getBean(AccountService.class); //Use an Init method instead
        AccountService accountService = new AccountService();
        //Account account = new Account(username,password);//Do we get a bean instance of Account here instead of inputting parameters?
        
        try{
            account = accountService.register(account);
        }catch(CredentialsInvalidException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(account);
        }catch(DuplicateUsernameException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(account);
        }
        
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(account);
    }

    //@PostMapping("login")
    //@ResponseStatus(HttpStatus.NO_CONTEXT)
    //public Account login(@RequestParam String username, @RequestParam String password){
        

    //}
    
    @ExceptionHandler(CredentialsInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String userInputInvalid(CredentialsInvalidException e){
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String duplicateUsername(DuplicateUsernameException e){
        return e.getMessage();
    }
}
