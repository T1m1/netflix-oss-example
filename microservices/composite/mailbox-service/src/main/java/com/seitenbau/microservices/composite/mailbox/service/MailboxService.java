package com.seitenbau.microservices.composite.mailbox.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailboxService {
	
	
	@Autowired
	MessageIntegration messageIntegration;
	
	@RequestMapping("/info")
    public String getStatus() {		
		 return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"I'm okay ;-)\"}";
    }
	
	@RequestMapping("/mailboxes/{userId}")
    public String getMailbox(@PathVariable String userId) {
		
		// 1. get all messages of user with userId
//		ResponseEntity<Message> messages = 
		
      return null;
    }
}
