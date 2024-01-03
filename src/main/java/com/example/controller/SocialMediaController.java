package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

//import com.example.exception.CredentialsInvalidException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @GetMapping("test/{account_id}")
    public String testMethod(@PathVariable int account_id){
        return "Henlo";
    }

    /*@PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) throws CredentialsInvalidException, DuplicateUsernameException{
        return ResponseEntity.status(HttpStatus.OK)
            .body(accountService.register(account));
    }*/
    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public Account register(@RequestBody Account account) throws NotSuccessfulException, DuplicateUsernameException{
        return accountService.register(account);
    }
    
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public Account login(@RequestBody Account account) throws CredentialsInvalidException, NotSuccessfulException{
        return accountService.login(account);
    }
    
    @GetMapping("accounts/{account_id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesFromAccount_id(@PathVariable int account_id){
        return messageService.getAllMessagesByAccountId(account_id);
    }



    //Happens for Registration, Create Message, Update Message
    @ExceptionHandler(NotSuccessfulException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notSuccessful(NotSuccessfulException e){
        return e.getMessage();
    }

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



    /*@PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        
        
        try{
            account = accountService.register(account);
        }catch(CredentialsInvalidException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(account);
        }catch(DuplicateUsernameException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(account);
        }
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(account);
    }*/
}
