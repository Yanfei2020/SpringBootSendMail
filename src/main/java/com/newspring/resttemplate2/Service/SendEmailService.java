package com.newspring.resttemplate2.Service;

import com.newspring.resttemplate2.domain.Email;

public interface SendEmailService {
	/**
	 * send email to SendGrid
	 * @return
	 */
	String doSendGrid(Email e);
	
	/**
	 * send email to MailGun
	 * @return
	 */
	String doMailGun(Email e);

}
