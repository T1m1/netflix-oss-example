package com.seitenbau.microservices.core.message;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.seitenbau.microservices.core.message.model.Message;


public interface MessageRepository extends MongoRepository<Message, String> {


}
