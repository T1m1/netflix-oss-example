package com.seitenbau.microservices.core.message;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.seitenbau.microservices.core.message.model.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

	public List<Message> findByToId(String toId);

	public List<Message> findByFromId(String fromId);

}
