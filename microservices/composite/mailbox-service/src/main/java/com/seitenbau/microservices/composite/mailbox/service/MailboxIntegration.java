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
		return getResponseAsList("message-service", "/messages/" + userId,
				new ParameterizedTypeReference<List<Message>>() {
				});
	}

	private <T> ResponseEntity<T> getResponseAsList(String serviceId,
			String requestURI,
			ParameterizedTypeReference<T> parameterizedTypeReference) {
		try {
			// get service URL for message-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					serviceId, false);

			// build request URL
			String url = instance.getHomePageUrl() + requestURI;

			restTemplate = new RestTemplate();
			ResponseEntity<T> responseList = restTemplate.exchange(url,
					HttpMethod.GET, null, parameterizedTypeReference);
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
		return getResponseAsObject("user-service", "/users/" + userId,
				User.class);
	}

	private <T> ResponseEntity<T> getResponseAsObject(String serviceId,
			String requestURI, Class<T> type) {
		try {
			// get service URL for user-service
			InstanceInfo instance = discoveryClient.getNextServerFromEureka(
					serviceId, false);

			// build request URL
			String getUserURL = instance.getHomePageUrl() + requestURI;

			// get user information
			restTemplate = new RestTemplate();
			T obj = restTemplate.getForObject(getUserURL, type);

			return createResponse(obj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	private <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
