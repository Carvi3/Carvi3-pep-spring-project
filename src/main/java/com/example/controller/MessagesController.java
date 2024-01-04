package com.example.controller;

//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("messages")
public class MessagesController{
    
    public MessageService messageService;
    public AccountService accountService;

    //@Autowired
    public MessagesController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
        //Was adding the extra parameter in the constructor really necessary???
        //It Fixed a NullPointerException from trying to make a call to the repository interface
        //TODO: Understand why a null pointer was being thrown. SQL/Query Issue.
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Message createMessage(@RequestBody Message message) throws NotSuccessfulException{
        Optional<Account> account = accountService.getAccountRepo().findById(message.getPosted_by());
        if(account.isPresent()){
            return messageService.createMessage(message);
        }
        throw new NotSuccessfulException();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @RequestMapping(path="{message_id}", method = RequestMethod.GET)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        Message queriedMessage = messageService.getMessageById(message_id);
        if(queriedMessage != null){
            return ResponseEntity.status(HttpStatus.OK)
            .body(queriedMessage);
        }else{
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(path="{message_id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public int updateText(@PathVariable int message_id, @RequestBody Message message) throws NotSuccessfulException{
        return messageService.updateTextById(message_id, message.getMessage_text());
    }
    
    @RequestMapping(path = "{message_id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id){
        int affectedRows = messageService.deleteMessage(message_id);
        if(affectedRows > 0){
            return ResponseEntity.status(HttpStatus.OK)
                .body(affectedRows);
        }
        return ResponseEntity.ok().build();
    }


    //Do we need the exceptionhandlers in this class too??
    @ExceptionHandler(NotSuccessfulException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notSuccessful(NotSuccessfulException e){
        return e.getMessage();
    }
}