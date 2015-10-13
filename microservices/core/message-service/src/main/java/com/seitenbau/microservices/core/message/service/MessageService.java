package com.seitenbau.microservices.core.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.message.MessageRepository;
import com.seitenbau.microservices.core.message.model.Message;

@RestController
public class MessageService {

	@Autowired
	private MessageRepository repository;

	@RequestMapping(value = "/outbox/{userId}", method = RequestMethod.GET, produces = "application/json")
	public List<Message> getOutboxFromUser(@PathVariable String userId) {
		return repository.findByFromId(userId);
	}
	
	@RequestMapping(value = "/inbox/{userId}", method = RequestMethod.GET, produces = "application/json")
	public List<Message> getInboxFromUser(@PathVariable String userId) {
		return repository.findByToId(userId);
	}
}
