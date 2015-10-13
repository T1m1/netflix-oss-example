package com.seitenbau.microservices.core.message.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Message {

	@Id
	private String messageId;
	private String toId;
	private String fromId;
	private String message;
	private String subject;
	private List<String> attachmentIds;

	public Message() {
	}

	public Message(String toId, String fromId, String message, String subject,
			List<String> attachmentIds) {
		this.toId = toId;
		this.fromId = fromId;
		this.message = message;
		this.subject = subject;
		this.attachmentIds = attachmentIds;
	}
}
