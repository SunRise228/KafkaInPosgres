package com.test.test.controller;

import com.test.test.model.Message;
import com.test.test.repository.MessageRepository;
import com.test.test.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private MessageService myFirstService = new MessageService();

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("getHello")
    public String getHello() {
        return myFirstService.sendHello();
    }

    @GetMapping("getBase")
    public Collection<Message> getBase() {
        return messageRepository.findAll();
    }

}
