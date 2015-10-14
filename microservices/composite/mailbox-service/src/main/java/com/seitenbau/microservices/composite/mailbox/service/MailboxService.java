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
import com.seitenbau.microservices.core.document.model.Document;
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
	@RequestMapping("/inbox/{userId}")
	public ResponseEntity<List<MailboxEntry>> getInboxOfUser(
			@PathVariable String userId) {

		// 1. get all messages of user with userId
		ResponseEntity<List<Message>> messages = mailboxIntegration
				.getMessagesSentToUser(userId);

		List<MailboxEntry> mailboxEntries = new ArrayList<MailboxEntry>();
		
		// return empty list if no messages found or any error
		if (!messages.getStatusCode().is2xxSuccessful()
				|| messages.getBody().isEmpty()) {
			return createResponse(mailboxEntries, messages.getStatusCode());
		}

		Set<String> userIds = getAllUserIDs(messages);
		Set<String> documentIds = getAllDocumentIDs(messages);
		HashMap<String, User> allUser = getAllUser(userIds);
		HashMap<String, Document> allDocuments = getAllDocuments(documentIds);

		mailboxEntries = buildMailboxEntries(messages.getBody(), allUser,
				allDocuments);

		return createResponse(mailboxEntries, messages.getStatusCode());
	}

	private Set<String> getAllDocumentIDs(ResponseEntity<List<Message>> messages) {
		// generate unique list with documents
		Set<String> documentIds = new LinkedHashSet<>();
		for (Message msg : messages.getBody()) {
			// add IDs of all attachments
			for (String attachmentId : msg.getAttachmentIds()) {
				documentIds.add(attachmentId);
			}
		}
		return documentIds;
	}

	private Set<String> getAllUserIDs(ResponseEntity<List<Message>> messages) {
		// 2. generate unique list with users
		Set<String> userIds = new LinkedHashSet<>();
		// add IDs of receiver
		userIds.add(messages.getBody().get(0).getToId());
		// add IDs of sender
		for (Message msg : messages.getBody()) {
			userIds.add(msg.getFromId());
		}
		return userIds;
	}

	// TODO generic method call
	private HashMap<String, User> getAllUser(Set<String> userIds) {
		HashMap<String, User> allUser = new HashMap<String, User>();

		// TODO RXJava - asynchrony
		for (String id : userIds) {
			ResponseEntity<User> user = mailboxIntegration.getUser(id);
			if (user.getStatusCode().is2xxSuccessful()) {
				allUser.put(id, user.getBody());
			}
		}
		return allUser;
	}

	private HashMap<String, Document> getAllDocuments(Set<String> documentIds) {
		HashMap<String, Document> allDocuments = new HashMap<String, Document>();

		for (String id : documentIds) {
			ResponseEntity<Document> document = mailboxIntegration
					.getDocument(id);
			if (document.getStatusCode().is2xxSuccessful()) {
				allDocuments.put(id, document.getBody());
			}
		}
		return allDocuments;
	}

	/**
	 * Build response object with all necessary information.
	 * 
	 * @param messages
	 * @param allUser
	 * @param allDocuments
	 * @return
	 */
	private List<MailboxEntry> buildMailboxEntries(List<Message> messages,
			HashMap<String, User> allUser,
			HashMap<String, Document> allDocuments) {

		List<MailboxEntry> entries = new ArrayList<MailboxEntry>();

		for (Message message : messages) {
			MailboxEntry mailboxEntry = new MailboxEntry();
			mailboxEntry.setMessageId(message.getMessageId());
			mailboxEntry.setUserFrom(allUser.get(message.getFromId()));
			mailboxEntry.setUserTo(allUser.get(message.getToId()));
			mailboxEntry.setMessage(message.getMessage());
			mailboxEntry.setSubject(message.getSubject());
			if (!allDocuments.isEmpty()) {
				ArrayList<Document> attachments = new ArrayList<Document>();
				for (String attachmentId : message.getAttachmentIds()) {
					attachments.add(allDocuments.get(attachmentId));
				}
				mailboxEntry.setAttachments(attachments);
			}

			entries.add(mailboxEntry);
		}
		return entries;
	}

	// TODO -> util class
	private <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
