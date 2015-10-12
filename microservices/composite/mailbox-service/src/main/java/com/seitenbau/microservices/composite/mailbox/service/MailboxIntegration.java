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
import com.seitenbau.microservices.core.user.model.User;

/**
 * TODO implement generic method
 * 
 * @author truprecht
 *
 */

// The @Component annotation marks a java class as a bean so the
// component-scanning mechanism
// of spring can pick it up and pull it into the application context
@Component
public class MailboxIntegration {

	// discovery client, to get service URL
	@Autowired
	private DiscoveryClient discoveryClient;

	private static RestTemplate restTemplate;

	/**
	 * Call message-service to get all messages with specific user id
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	public ResponseEntity<List<Message>> getMessagesSentToUser(String userId) {
		return getResponseAsList("message-service", "/messages/" + userId);
	}

	public <T> ResponseEntity<List<T>> getResponseAsList(String serviceId,
			String requestURI) {
		try {
			// get service URL for message-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					serviceId, false);

			// build request URL
			String url = instance.getHomePageUrl() + requestURI;

			restTemplate = new RestTemplate();
			ResponseEntity<List<T>> responseList = restTemplate.exchange(url,
					HttpMethod.GET, null,
					new ParameterizedTypeReference<List<T>>() {
					});
			return responseList;
		} catch (Exception e) {
			System.out.println(e);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Call user-service to get user with userId
	 * 
	 * @param userId
	 * @return all message of user with userId
	 */
	public ResponseEntity<User> getUser(String userId) {
		try {
			// get service URL for user-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					"user-service", false);

			// build request URL
			String getUserURL = instance.getHomePageUrl() + "/users/" + userId;

			// get user information
			restTemplate = new RestTemplate();
			User user = restTemplate.getForObject(getUserURL, User.class);

			return createResponse(user, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
