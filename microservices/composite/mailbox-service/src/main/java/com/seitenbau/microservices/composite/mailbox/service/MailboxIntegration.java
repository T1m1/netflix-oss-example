package com.seitenbau.microservices.composite.mailbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.seitenbau.microservices.core.document.model.Document;
import com.seitenbau.microservices.core.message.model.Message;
import com.seitenbau.microservices.core.user.model.User;


// The @Component annotation marks a java class as a bean so the
// component-scanning mechanism
// of spring can pick it up and pull it into the application context
@Component
public class MailboxIntegration {

	@Autowired
	private LoadBalancerClient loadBalancer;

	private RestTemplate restTemplate;

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

	/**
	 * Get document information of specific document
	 * 
	 * @param documentId
	 * @return
	 */
	public ResponseEntity<Document> getDocument(String documentId) {
		return getResponseAsObject("document-service", "/documents/"
				+ documentId, Document.class);
	}

	private <T> ResponseEntity<T> getResponseAsList(String serviceId,
			String requestURI,
			ParameterizedTypeReference<T> parameterizedTypeReference) {
		try {
			String url = getRequestUrl(serviceId, requestURI);

			restTemplate = new RestTemplate();
			ResponseEntity<T> responseList = restTemplate.exchange(url,
					HttpMethod.GET, null, parameterizedTypeReference);
			return responseList;
		} catch (Exception e) {
			System.out.println(e);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	private <T> ResponseEntity<T> getResponseAsObject(String serviceId,
			String requestURI, Class<T> type) {
		try {
			String getUserURL = getRequestUrl(serviceId, requestURI);

			// get user information
			restTemplate = new RestTemplate();
			T obj = restTemplate.getForObject(getUserURL, type);

			return createResponse(obj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	private String getRequestUrl(String serviceId, String requestURI) {
		// get service URL for message-service
		ServiceInstance instance = loadBalancer.choose(serviceId);

		// build request URL
		return instance.getUri() + requestURI;
	}

	private <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}
}
