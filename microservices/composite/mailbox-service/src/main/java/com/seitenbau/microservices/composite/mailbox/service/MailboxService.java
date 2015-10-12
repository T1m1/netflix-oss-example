package com.seitenbau.microservices.composite.mailbox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.composite.mailbox.model.MailboxEntry;
import com.seitenbau.microservices.core.message.model.Message;
import com.seitenbau.microservices.core.user.model.User;

@RestController
public class MailboxService {

	@Autowired
	MailboxIntegration mailboxIntegration;

	@RequestMapping("/info")
	public String getStatus() {
		return "{\"timestamp\":\"" + new Date()
				+ "\",\"content\":\"I'm okay ;-)\"}";
	}

	/**
	 * Get a list of all mails including all required information.
	 * 
	 * @param userId
	 * @return list with all mails
	 */
	@RequestMapping("/mailboxes/{userId}")
	public ResponseEntity<List<MailboxEntry>> getMailbox(
			@PathVariable String userId) {

		// 1. get all messages of user with userId
		ResponseEntity<List<Message>> messages = mailboxIntegration
				.getMessagesSentToUser(userId);

		// return empty list if no messages found
		if (!messages.getStatusCode().is2xxSuccessful()) {
			return createResponse(null, messages.getStatusCode());
		}

		// 2. generate unique list with users
		Set<String> userIds = new LinkedHashSet<>();

		// add IDs of receiver
		userIds.add(messages.getBody().get(0).getToId());
		// add IDs of sender
		for (Message msg : messages.getBody()) {
			userIds.add(msg.getFromId());
		}

		HashMap<String, User> allUser = new HashMap<String, User>();

		// TODO RXJava - asynchrony
		for (String id : userIds) {
			ResponseEntity<User> user = mailboxIntegration.getUser(id);
			if (user.getStatusCode().is2xxSuccessful()) {
				allUser.put(id, user.getBody());
			}
		}

		List<MailboxEntry> mailboxEntries = buildMailboxEntries(
				messages.getBody(), allUser);

		// TODO get documents

		return createResponse(mailboxEntries, messages.getStatusCode());
	}

	/**
	 * Build response object with all necessary information.
	 * 
	 * @param messages
	 * @param allUser
	 * @return
	 */
	private List<MailboxEntry> buildMailboxEntries(List<Message> messages,
			HashMap<String, User> allUser) {

		List<MailboxEntry> entries = new ArrayList<MailboxEntry>();

		for (Message message : messages) {
			MailboxEntry mailboxEntry = new MailboxEntry();
			mailboxEntry.setMessageId(message.getMessageId());
			mailboxEntry.setUserFrom(allUser.get(message.getFromId()));
			mailboxEntry.setUserTo(allUser.get(message.getToId()));
			mailboxEntry.setMessage(message.getMessage());
			mailboxEntry.setSubject(message.getSubject());
			entries.add(mailboxEntry);
		}
		return entries;
	}

	// TODO -> util class
	private <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}

}
