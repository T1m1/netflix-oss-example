package com.seitenbau.microservices.core.document.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Document {

	@Id
	private String documentId;
	private String name;

	public Document() {

	}

	public Document(String documentId, String name) {
		this.documentId = documentId;
		this.name = name;
	}
}
