package com.seitenbau.microservices.core.message.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.message.model.Message;

@RestController
public class MessageService {
	
	@RequestMapping("/messages/{messageId}")
    public Message getMessage(@PathVariable String messageId) {		
		// TMP-MOCK
        return new Message(messageId, "1a2b3c4e", "0z1y2x3w", "Hello Word", "Huhu");
    }

}
