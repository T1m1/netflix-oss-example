package com.seitenbau.microservices.core.document;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.seitenbau.microservices.core.document.model.Document;

public interface DocumentRepository extends MongoRepository<Document, String> {

}
