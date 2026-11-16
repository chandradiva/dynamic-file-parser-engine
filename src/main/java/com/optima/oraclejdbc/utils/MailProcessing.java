package com.optima.oraclejdbc.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.stereotype.Component;

import javax.activation.*;
import javax.annotation.Resource;

@Component("mailProcessing")
public class MailProcessing {

//	private static Properties properties;
	
	@Resource(name="confProp")
	private Properties properties;
	
	public void sendEmail(String from, String to, String subject, String body, List<String> attachments){
		String host = (String) properties.get("smtp_server");
		String port = (String) properties.get("smtp_port");
		final String username = (String) properties.get("username");
		final String password = (String) properties.get("password");
		// Get system properties
		Properties sysProp = new Properties();
		
		// Setup mail server
		sysProp.setProperty("mail.smtp.host", host);
		sysProp.setProperty("mail.smtp.port", port);
		Authenticator auth = new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session session = Session.getInstance(sysProp, auth);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			/*Header*/
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	        message.setSubject(subject);
	       
	        // Create a multipart message
	        Multipart multipart = new MimeMultipart();

	        /*Body*/
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(body);
	        
	        // Set text message part
	        multipart.addBodyPart(messageBodyPart);

	        if(attachments.size()>0){
	        	for(String attachment : attachments){
	        		messageBodyPart = new MimeBodyPart();
			        String filename = attachment;
			        DataSource source = new FileDataSource(filename);
			        messageBodyPart.setDataHandler(new DataHandler(source));
			        File f = new File(filename);
			        
			        messageBodyPart.setFileName(f.getName());
			        multipart.addBodyPart(messageBodyPart);
	        	}
	        }
	        
	        // Send the complete message parts
	        message.setContent(multipart );

	        // Send message
	        Transport.send(message);
	        System.out.println("Sent message successfully....");
	     }catch (MessagingException mex) {
	        mex.printStackTrace();
	     }
	}
	
//	public void setProperties(Properties confProp){
//		MailProcessing.properties = confProp;
//	}
}
