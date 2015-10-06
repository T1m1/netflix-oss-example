package com.seitenbau.microservices.core.document.model;

import lombok.Data;

@Data
public class Document {

	private String documentId;
	private String name;

	public Document(String documentId, String name) {
		this.documentId = documentId;
		this.name = name;
	}
}
