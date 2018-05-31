package com.newspring.resttemplate2.Utils;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.newspring.resttemplate2.domain.Email;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CovertData {
	
	/**
	 * split data if multiple recipients
	 * @param tosAndccs
	 * @return
	 */
	public static JSONArray splitData(String tosAndccs) {
		//assemble data
		JSONArray dataArray = new JSONArray();
		if(tosAndccs.contains(",")) {
			String[] toccStr = tosAndccs.split(",");
			for(int i = 0;i<toccStr.length;i++) {
				JSONObject dataObject = new JSONObject();
				dataObject.put("email", toccStr[i]);
				dataArray.add(dataObject);
			}
		}else {
			JSONObject dataObject = new JSONObject();
			dataObject.put("email", tosAndccs);
			dataArray.add(dataObject);
		}
		
		return dataArray;
	}
	
	/**
	 * Assemble data to JSONObject
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 * @return
	 */
	public static JSONObject assembleData(String to,String cc,String bcc,String subject,String content) {
		
		JSONArray toArray = splitData(to);
		JSONArray ccArray = splitData(cc);
		
		JSONObject toEmail = new JSONObject();
 		toEmail.put("to", toArray);
 		JSONObject ccEmail = new JSONObject();
 		ccEmail.put("cc", ccArray);
 		JSONObject fromObject =  new JSONObject();
 		fromObject.put("email", "chenyanfei1124@gmail.com");
		
 		//assemble content data
 		JSONArray contentArray = new JSONArray();
 		JSONObject typeObject =  new JSONObject();
 		typeObject.put("type", "text/plain");
 		typeObject.put("value", content);
 		contentArray.add(typeObject);
 		
 		JSONArray personArray = new JSONArray();
 		personArray.add(toEmail);
 		personArray.add(ccEmail);
 		
 		JSONObject data =  new JSONObject();
 		data.put("personalizations", personArray);
 		data.put("from", fromObject);
 		data.put("subject", subject);
 		data.put("content", contentArray);
		
		return data;
	}
	
	/**
	 * Assemble data to JSONObject
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param content
	 * @return
	 */
	public static JSONObject assembleSendGridData(Email email) {
		String to = email.getTo();
		String subject = email.getSubject();
		String content = email.getContent();
		
		JSONArray toArray = splitData(to);
		JSONObject toEmail = new JSONObject();
 		toEmail.put("to", toArray);
 		
 		JSONObject fromObject =  new JSONObject();
 		fromObject.put("email", "chenyanfei1124@gmail.com");
		
 		//assemble content data
 		JSONArray contentArray = new JSONArray();
 		JSONObject typeObject =  new JSONObject();
 		typeObject.put("type", "text/plain");
 		typeObject.put("value", content);
 		contentArray.add(typeObject);
 		
 		JSONArray personArray = new JSONArray();
 		personArray.add(toEmail);
 		
 		JSONObject data =  new JSONObject();
 		data.put("personalizations", personArray);
 		data.put("from", fromObject);
 		data.put("subject", subject);
 		data.put("content", contentArray);
		
		return data;
	}
	/**
	 * assemble MailGun Data
	 * @param email
	 * @return
	 */
	public static MultiValueMap<String, String> assembleMailGunData(Email email){
		MultiValueMap<String, String> dataMap= new LinkedMultiValueMap<String, String>();
		dataMap.add("to", email.getTo());
		dataMap.add("subject", email.getSubject());
		dataMap.add("text", email.getContent());
		return dataMap;
	}

}
