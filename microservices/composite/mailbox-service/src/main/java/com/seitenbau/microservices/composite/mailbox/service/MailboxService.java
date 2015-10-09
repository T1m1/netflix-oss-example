package com.seitenbau.microservices.composite.mailbox.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.message.model.Message;
import com.seitenbau.microservices.core.user.model.User;

@RestController
public class MailboxService {

	@Autowired
	MessageIntegration messageIntegration;

	@RequestMapping("/info")
	public String getStatus() {
		return "{\"timestamp\":\"" + new Date()
				+ "\",\"content\":\"I'm okay ;-)\"}";
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/mailboxes/{userId}")
	public ResponseEntity<List<Message>> getMailbox(@PathVariable String userId) {

		// 1. get all messages of user with userId
		ResponseEntity<List<Message>> messages = messageIntegration
				.getMessagesSentToUser(userId);
		
		// return empty list if no messages found
		if(messages.getBody().isEmpty()) {
			return messages;
		}
		
		// 2. generate unique list with users
		Set<String> userIds = new LinkedHashSet<>();
		
		// add IDs of receiver
		userIds.add(messages.getBody().get(0).getToId());
		// add IDs of sender
		for(Message msg : messages.getBody()) {
			userIds.add(msg.getFromId());
		}
		
		HashMap<String, User> allUser = new HashMap<String, User>();
		
		

		return messages;
	}
	
}
