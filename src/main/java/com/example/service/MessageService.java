package com.example.service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public boolean messageRequirement(String message_text){
        return message_text.length() == 0? false : 
            message_text.length() < 255? true : false;
    }
    /*public boolean userExists(Message message) throws NotSuccessfulException{
        accountService = new AccountService();
        return accountService.getAccountRepo().existsById(message.getPosted_by());
    }*/

    //Create

    public Message createMessage(Message message) throws NotSuccessfulException{
        if(messageRequirement(message.getMessage_text())){
            return messageRepository.save(message);
        }
        throw new NotSuccessfulException();
    }

    //Read

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get(); //TODO: how come .getById didn't work???
        }
        return null;
        /*if(messageRepository.existsById(message_id)){
            return messageRepository.findById(message_id);
        }
        return null;*/
    }

    public List<Message> getAllMessagesByAccountId(int account_id){
        return messageRepository.findByPosted_by(account_id);
    }
    /*List<Message> queryList = messageRepository.findByPosted_by(account_id);*/
        /*Iterator<Message> iterator = queryList.iterator();
        int i = 1;
        while(iterator.hasNext()){
            System.out.println("Message("+i+") :" + iterator.next().toString());
            i++;
        }*/

        /*return queryList;*/

    //Update
    public int updateTextById(int message_id, String newText) throws NotSuccessfulException{
        if(!messageRepository.existsById(message_id)){
            throw new NotSuccessfulException("Message_id not found");
        }
        if(messageRequirement(newText)){
            Message message = messageRepository.getById(message_id);
            message.setMessage_text(newText);
            messageRepository.save(message);
            return 1; //TODO: Make sure to find out how to get affected rows from .save query
        }
        throw new NotSuccessfulException("Message_text Requirement not met");
    }


    //Delete
    public int deleteMessage(int message_id){
        if(!messageRepository.existsById(message_id)){
            return 0;
        }
        messageRepository.deleteById(message_id);
        return 1;
    }
}
