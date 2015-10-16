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
}
