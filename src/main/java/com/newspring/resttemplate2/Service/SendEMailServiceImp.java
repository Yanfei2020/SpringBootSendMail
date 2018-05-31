package com.newspring.resttemplate2.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.newspring.resttemplate2.Utils.CovertData;
import com.newspring.resttemplate2.domain.Email;

import net.sf.json.JSONObject;

@Service
public class SendEMailServiceImp implements SendEmailService {
	
	@Value("${SendGrid.apiKey}")
	private String sApiKey;
	
	@Value("${SendGrid.url}")
	private String sUrl;
	
	@Value("${MailGun.from}")
	private String sFrom;
	
	@Value("${MailGun.apiKey}")
	private String mApikey;
	
	@Value("${MailGun.url}")
	private String mUrl;
	
	@Value("${SendGrid.from}")
	private String mFrom;
	

	@Override
	public String doSendGrid(Email email) {
		RestTemplate restTemplate = new RestTemplate();
		//Header 数据
		HttpHeaders headers = new HttpHeaders();
 		headers.set("Authorization","Bearer "+sApiKey);
 		headers.set("Content-Type", "application/json");
 		
 		JSONObject data = CovertData.assembleSendGridData(email);
 		HttpEntity<String> request = new HttpEntity<String>(data.toString(),headers);
 		
 		ResponseEntity response = restTemplate.exchange(sUrl, HttpMethod.POST, request, String.class);
 		HttpStatus re = response.getStatusCode();
 		String reStr = re.toString();
 		
		return reStr;
	}

	@Override
	public String doMailGun(Email email) {
		RestTemplate restTemplate = new RestTemplate();
		String encodeKey = new sun.misc.BASE64Encoder().encode (mApikey.getBytes());
		
		HttpHeaders headers = new HttpHeaders();
 		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
 		headers.set("Authorization","Basic "+encodeKey);
 		
 		MultiValueMap<String, String> data = CovertData.assembleMailGunData(email);
 		
 		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(data, headers);
 		ResponseEntity<String> response = restTemplate.postForEntity(mUrl, request , String.class );
 		HttpStatus re = response.getStatusCode();
 		String reStr = re.toString();
		
		return reStr;
	}

}
