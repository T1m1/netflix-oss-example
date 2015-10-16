package com.seitenbau.microservices.composite.mailbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.seitenbau.microservices.core.document.model.Document;
import com.seitenbau.microservices.core.message.model.Message;
import com.seitenbau.microservices.core.user.model.User;

// The @Component annotation marks a java class as a bean so the
// component-scanning mechanism
// of spring can pick it up and pull it into the application context
@Component
public class MailboxIntegration {

	@Autowired
	private Util util;

	/**
	 * Call message-service to get all messages with specific user id
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	public ResponseEntity<List<Message>> getMessagesSentToUser(String userId) {
		return util.getResponseAsList("message-service", "/messages/inbox/"
				+ userId, new ParameterizedTypeReference<List<Message>>() {
		});
	}

	/**
	 * Call user-service to get user with userId
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	@HystrixCommand(fallbackMethod = "defaultUsers")
	public ResponseEntity<User> getUser(String userId) {
		return util.getResponseAsObject("user-service", "/users/" + userId,
				User.class);
	}

	/**
	 * Fallback Method for users
	 * 
	 * @param userId
	 * @return
	 */
	public ResponseEntity<User> defaultUsers(String userId) {
		return util.createResponse(null, HttpStatus.BAD_GATEWAY);
	}

	/**
	 * Get document information of specific document
	 * 
	 * @param documentId
	 * @return
	 */
	public ResponseEntity<Document> getDocument(String documentId) {
		return util.getResponseAsObject("document-service", "/documents/"
				+ documentId, Document.class);
	}

}
