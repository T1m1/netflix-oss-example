package com.seitenbau.microservices.composite.mailbox.model;

import com.seitenbau.microservices.core.user.model.User;

import lombok.Data;

@Data
public class MailboxEntry {

	private String messageId;
	private User userFrom;
	private User userTo;
	private String message;
	private String subject;

	public MailboxEntry() {

	}
}
