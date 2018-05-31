package com.newspring.resttemplate2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpMailGun {
	@GetMapping("/sendMailGun")
	public String sendMailGun() {
		
		String urlStr = "https://api.mailgun.net/v3/sandbox2b80f6a98ddc421eadf1390a8a937b01.mailgun.org/messages";
		String fromStr = "postmaster@sandbox2b80f6a98ddc421eadf1390a8a937b01.mailgun.org";	
		String toStr = "348089382@qq.com";
		String subjectStr = "Happy";
		String textStr = "test if you are happy";
		String apiKeyStr = "api:key-e38d18639059d061a4cc4f6eff97f7d1";
		
		String encodeKey = new sun.misc.BASE64Encoder().encode (apiKeyStr.getBytes());
		
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	    	url = new URL(urlStr);
	    	connection = (HttpURLConnection)url.openConnection();
	    	connection.setRequestMethod("POST");
	    	connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	    	connection.setRequestProperty("Authorization","Basic "+encodeKey); 
	    	connection.setRequestProperty("Content-Language", "text/plain");
	    	connection.setRequestProperty("charset", "UTF-8");
	    	connection.setUseCaches (false);
	    	connection.setDoInput(true);
	    	connection.setDoOutput(true);

	    	//Send request
	    	OutputStream os = connection.getOutputStream();    
         	String param = new String();    
         	param = "from=" + fromStr +    
                  "&to=" + toStr+    
                  "&subject=" + subjectStr +    
                  "&text=" + java.net.URLEncoder.encode(textStr,"GBK"); 
         	os.write(param.getBytes());
         	os.flush();
         	os.close();
          
         	int responseCode = connection.getResponseCode();
         	System.out.println("Response Code------------------------------------------ : " + responseCode);
         	//Get Response	
         	InputStream is = connection.getInputStream();
         	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         	String line;
         	StringBuffer response = new StringBuffer(); 
         	while((line = rd.readLine()) != null) {
         		response.append(line);
         		response.append('\r');
         	}
         	System.out.println("-----"+response.toString());
         	rd.close();
         	return responseCode+"";
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return e+"";
	    } finally {
	    	if(connection != null) {
	    		connection.disconnect(); 
	    	}
	    }
	}
	
}
