package com.seitenbau.microservices.composite.mailbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.seitenbau.microservices.core.message.model.Message;

//The @Component annotation marks a java class as a bean so the component-scanning mechanism 
// of spring can pick it up and pull it into the application context
@Component
public class MessageIntegration {

	// discovery client, to get service URL
	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Call message microservice and get all messages with specific user id
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	public ResponseEntity<Message[]> getMessagesFromUserId(
			@PathVariable String userId) {
		try {
			// get service URL for message-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					"message-service", false);

			// build request URL
			String getMessagesURL = instance.getHomePageUrl() + "/messages/"
					+ userId;

			// create new rest template
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Message[]> responseEntity = restTemplate
					.getForEntity(getMessagesURL, Message[].class);

			return createResponse(responseEntity.getBody(), HttpStatus.OK);
		} catch (Exception e) {
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
