package com.seitenbau.microservices.composite.mailbox.model;

import java.util.List;

import com.seitenbau.microservices.core.document.model.Document;
import com.seitenbau.microservices.core.user.model.User;

import lombok.Data;

@Data
public class MailboxEntry {

	private String messageId;
	private User userFrom;
	private User userTo;
	private String message;
	private String subject;
	private List<Document> attachments;

	public MailboxEntry() {

	}
}
