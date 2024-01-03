package com.example.controller;

//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.*;

import java.util.Optional;

@RestController
@RequestMapping("messages")
public class MessagesController{
    
    public MessageService messageService;
    //public AccountService accountService;

    @Autowired
    public MessagesController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Message createMessage(@RequestBody Message message) throws NotSuccessfulException{
        //Optional<Account> account = accountService.getAccountRepo().findById(message.getPosted_by());
        //if(account.isPresent()){
            return messageService.createMessage(message);
        //}
        //throw new NotSuccessfulException();
    }
    

    //Do we need the exceptionhandlers in this class too??
    @ExceptionHandler(NotSuccessfulException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notSuccessful(NotSuccessfulException e){
        return e.getMessage();
    }
}