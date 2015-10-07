package com.seitenbau.microservices.composite.mailbox.service;

import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailboxService {
	
	@RequestMapping("/mailboxes/{mailboxId}")
    public String getMailbox(@PathVariable String mailboxId) {		
      return null;
    }
	
	@RequestMapping("/info")
    public String getStatus() {		
		 return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"I'm okay ;-)\"}";
    }

}
