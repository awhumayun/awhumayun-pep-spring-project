package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public Account registerUser(@RequestBody Account account) throws Exception {
        return accountService.registerUser(account);
    }

    @PostMapping("/login")
    public Account loginUser(@RequestBody Account account) throws Exception {
        return accountService.loginUser(account.getUsername(), account.getPassword());
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) throws Exception {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{id}")
    public Message getMessageById(@PathVariable int id) {
        return messageService.getMessageById(id);
    }

    @DeleteMapping("/messages/{id}")
    public String deleteMessage(@PathVariable int id) {
        return messageService.deleteMessage(id);
    }

    @PatchMapping("/messages/{id}")
    public String updateMessage(@PathVariable int id, @RequestBody Message message) throws Exception {
        return messageService.updateMessage(id, message.getMessageText());
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getAllMessagesByUser(@PathVariable int accountId) {
        return messageService.getAllMessagesByUser(accountId);
    }
}
