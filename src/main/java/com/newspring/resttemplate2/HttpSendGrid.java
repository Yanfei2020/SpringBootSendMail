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
public class HttpSendGrid {
	@GetMapping("/sendSendGrid")
	public void sendSendGrid() {
		String urlStr = "https://api.sendgrid.com/v3/mail/send";
		String fromStr = "chenyanfei1124@gmail.com";
		String toStr = "chenyanfei_2012@163.com";
		String subjectStr = "Hello";
		String textStr = "Welcome to earth!";
		String apiKeyStr = "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";
		
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	    	url = new URL(urlStr);
	    	connection = (HttpURLConnection)url.openConnection();
	    	connection.setRequestMethod("POST");
	    	connection.setRequestProperty("Content-Type","application/json");
	    	connection.setRequestProperty("Authorization","Bearer "+apiKeyStr); 
	    	connection.setRequestProperty("Content-Type", "text/plain");
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
                  "&content=" + java.net.URLEncoder.encode(textStr,"GBK"); 
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
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	if(connection != null) {
	    		connection.disconnect(); 
	    	}
	    }
	}
}
