package com.seitenbau.microservices.core.document;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.seitenbau.microservices.core.document.model.Document;

public interface DocumentRepository extends MongoRepository<Document, String> {

}
