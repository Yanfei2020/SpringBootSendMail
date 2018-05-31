package com.newspring.resttemplate2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.newspring.resttemplate2.domain.Email;
import com.newspring.resttemplate2.Service.SendEmailService;

@RestController
public class SendEmailController {
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@RequestMapping("/DoSend")
	public String DoSend() {
 		//get data from front-end
 		String to = "348089382@qq.com";//if multiple recipients,please use ',' contact
 		String subject = "welcome";
 		String content = "welcome to Earth";
 		
 		Email email = new Email();
 		email.setTo(to);
 		email.setSubject(subject);
 		email.setContent(content);
 		//default send email by sendGrid
 		String result = sendEmailService.doSendGrid(email);
 		if(result.startsWith("2") && !result.equals("200")) {
 			return "success";
 		}else {
 			//if failed, send email by MailGun
 			result = sendEmailService.doSendGrid(email);
 			return result;
 		}
	}

}
