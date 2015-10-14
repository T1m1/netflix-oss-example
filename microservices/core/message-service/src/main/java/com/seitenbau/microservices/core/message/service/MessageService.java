package com.seitenbau.microservices.core.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.message.MessageRepository;
import com.seitenbau.microservices.core.message.model.Message;

@RestController
@RequestMapping("/messages")
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

	@RequestMapping(method = RequestMethod.GET)
	public List<Message> getAllMessages() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public void create(@RequestBody Message message) {
		repository.save(message);
	}

	@RequestMapping(value = "{messageId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String messageId) {
		repository.delete(messageId);
	}

	@RequestMapping(value = "{messageId}", method = RequestMethod.PUT, consumes = "application/json")
	public Message update(@PathVariable String messageId,
			@RequestBody Message message) {
		Message update = repository.findOne(messageId);
		update.setFromId(message.getFromId());
		update.setToId(message.getToId());
		update.setMessage(message.getMessage());
		update.setSubject(message.getSubject());
		update.setAttachmentIds(message.getAttachmentIds());
		return repository.save(update);
	}

}
