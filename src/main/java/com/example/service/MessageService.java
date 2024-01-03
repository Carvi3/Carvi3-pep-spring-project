package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    //private AccountRepository accountRepository; //I felt that this was inevitable
    //Should this account query be in the controller or in Service layer?

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public boolean messageRequirement(String message_text){
        return message_text.length() == 0? false : 
            message_text.length() < 255? true : false;
    }
    /*public boolean userExists(Message message){
        Optional<Account> account = accountRepository.findById(message.getPosted_by());
        return account.isPresent()? true:false;
    }*/

    public Message createMessage(Message message) throws NotSuccessfulException{
        if(messageRequirement(message.getMessage_text()) /*&& userExists(message)*/){
            return messageRepository.save(message);
        }
        throw new NotSuccessfulException();
    }

    
}
