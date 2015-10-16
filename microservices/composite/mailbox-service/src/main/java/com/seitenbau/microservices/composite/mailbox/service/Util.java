package com.seitenbau.microservices.composite.mailbox.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Util {

	private static final Logger LOG = LoggerFactory.getLogger(Util.class);

	@Autowired
	private LoadBalancerClient loadBalancer;

	private RestTemplate restTemplate;

	@PostConstruct
	private void init() {
		this.restTemplate = new RestTemplate();
	}

	protected <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
		return new ResponseEntity<>(body, httpStatus);
	}

	protected <T> ResponseEntity<T> getResponseAsObject(String serviceId,
			String requestURI, Class<T> type) {
		try {
			T obj = getRequestObject(serviceId, requestURI, type);

			return createResponse(obj, HttpStatus.OK);
		} catch (Exception e) {
			LOG.warn("Failed to resolve serviceId '{}' with requestURI '{}",
					serviceId, requestURI);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	protected <T> T getRequestObject(String serviceId, String requestURI,
			Class<T> type) {
		String getUserURL = getRequestUrl(serviceId, requestURI);
		T obj = restTemplate.getForObject(getUserURL, type);
		return obj;
	}

	protected <T> ResponseEntity<T> getResponseAsList(String serviceId,
			String requestURI,
			ParameterizedTypeReference<T> parameterizedTypeReference) {
		try {
			String url = getRequestUrl(serviceId, requestURI);

			return restTemplate.exchange(url, HttpMethod.GET, null,
					parameterizedTypeReference);
		} catch (Exception e) {
			LOG.warn("Failed to resolve serviceId '{}' with requestURI '{}",
					serviceId, requestURI);
			return createResponse(null, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	protected String getRequestUrl(String serviceId, String requestURI) {
		// get service URL for message-service
		ServiceInstance instance = loadBalancer.choose(serviceId);
		// build request URL
		return instance.getUri() + requestURI;
	}
}
