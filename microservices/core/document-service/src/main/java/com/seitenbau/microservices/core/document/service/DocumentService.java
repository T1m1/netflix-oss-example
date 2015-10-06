package com.seitenbau.microservices.core.document.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seitenbau.microservices.core.document.model.Document;


@RestController
public class DocumentService {
	
	@RequestMapping("/documents/{documentId}")
    public Document getDocument(@PathVariable String documentId) {		
        return new Document(documentId, "mypdf");
    }

}
