package com.seitenbau.microservices.core.document.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.document.DocumentRepository;
import com.seitenbau.microservices.core.document.model.Document;

@RestController
@RequestMapping("/documents")
public class DocumentService {

	@Autowired
	private DocumentRepository repository;

	@RequestMapping(value = "{documentId}", method = RequestMethod.GET, produces = "application/json")
	public Document getDocument(@PathVariable String documentId) {
		return repository.findOne(documentId);
	}

}
