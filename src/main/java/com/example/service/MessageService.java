package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) throws Exception {
        if (message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text is either empty or too long");
        }
        else if (!accountRepository.existsById(message.getPostedBy())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not exist");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public String deleteMessage(int id) {
        Message messageToDelete = messageRepository.findById(id).orElse(null);
        
        if (messageToDelete != null) {
            messageRepository.deleteById(id);
            return "1";
        }   
        
        return "";
    }

    public String updateMessage(int id, String newText) throws Exception {
        if (newText == null || newText.isEmpty() || newText.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Message text must not be blank and must be under 255 characters");
        }
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Message not found");
        }

        message.setMessageText(newText);
        messageRepository.save(message);

        return "1";
    }

    public List<Message> getAllMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
