package com.seitenbau.microservices.core.message.model;

import java.util.List;

import lombok.Data;

@Data
public class Message {

	private String messageId;
	private String toId;
	private String fromId;
	private String message;
	private String subject;
	private List<String> attachment;

	public Message() {
	}

	public Message(String messageId, String toId, String fromId,
			String message, String subject, List<String> attachment) {
		this.messageId = messageId;
		this.toId = toId;
		this.fromId = fromId;
		this.message = message;
		this.subject = subject;
		this.attachment = attachment;
	}
}
