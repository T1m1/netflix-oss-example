package com.seitenbau.microservices.composite.mailbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.seitenbau.microservices.core.message.model.Message;

//The @Component annotation marks a java class as a bean so the component-scanning mechanism 
// of spring can pick it up and pull it into the application context
@Component
public class MessageServiceIntegration {

	// discovery client, to get service URL
	@Autowired
	private DiscoveryClient discoveryClient;

	private static RestTemplate restTemplate;

	/**
	 * Call message microservice and get all messages with specific user id
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	public ResponseEntity<List<Message>> getMessagesSentToUser(String userId) {
		try {
			// get service URL for message-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					"message-service", false);

			// build request URL
			String getMessagesURL = instance.getHomePageUrl() + "/messages/"
					+ userId;

			// # alternative - get messages as array and convert to list
			// Message[] messagesAsArray = restTemplate.getForObject(
			// getMessagesURL, Message[].class);
			// new ResponseEntity<List<Message>>(
			// Arrays.asList(messagesAsArray), HttpStatus.OK);

			ResponseEntity<List<Message>> myMessageList = restTemplate
					.exchange(getMessagesURL, HttpMethod.GET, null,
							new ParameterizedTypeReference<List<Message>>() {
							});

			return myMessageList;
		} catch (Exception e) {
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
