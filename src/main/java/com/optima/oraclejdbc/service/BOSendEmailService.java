package com.optima.oraclejdbc.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.cons.SendEmailStatus;
import com.optima.oraclejdbc.daoimpl.BOSendEmailDao;
import com.optima.oraclejdbc.model.backoffice.BOSendEmail;
import com.optima.oraclejdbc.model.backoffice.BOSendEmailLog;

@Service
public class BOSendEmailService {
	@Autowired
	BOSendEmailDao dao;
	
	@Resource(name="confProp")
	private java.util.Properties confProperties;
	
	public BOSendEmail updateEmailStatus(long id, int status) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		BOSendEmail email = dao.getEmailById(id);
		if( email != null ){
			email.setStatus(status);
			dao.update(email);
		}
		return email;
	}
	
	public void processNewEmail() throws MessagingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		String protocol = confProperties.getProperty("read_mail_protocol");
		String host = confProperties.getProperty("read_mail_server");
		String port = confProperties.getProperty("read_mail_port");
		String username = confProperties.getProperty("username");
		String password = confProperties.getProperty("password");
		Properties properties = new Properties();
		 
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
 
        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));
        
        Session session = Session.getInstance(properties);
        
        Store store = session.getStore(protocol);
        store.connect(username, password);
 
        // opens the inbox folder
        Folder folderInbox = store.getFolder("INBOX");
        folderInbox.open(Folder.READ_WRITE);
     
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        Message messages[] = folderInbox.search(unseenFlagTerm);
        
        for (int i = 0; i < messages.length; i++) {
    	    Message msg = messages[i];
    	    String contentType = msg.getContentType().toLowerCase();
            String messageContent = "";
            String mailTo = msg.getFrom()[0].toString();
            String subject = msg.getSubject();
            boolean goProcess = false;
            if (contentType.contains("text/plain")
                    || contentType.contains("text/html")) {
        	    Object content = msg.getContent();
                if (content != null) {
                    messageContent = content.toString();
                    goProcess = true;
                }
            }else if( contentType.contains("multipart")){
        	    Multipart mp = (Multipart)msg.getContent();
        	    int count = mp.getCount();
        	    for(int j=0;j<count;j++){
        		    Part part = mp.getBodyPart(j);
        		    String partType = part.getContentType().toLowerCase();
        		    if( partType.contains("text/plain") ||contentType.contains("text/html")){
        			    Object content = part.getContent();
        			    if (content != null) {
	                        messageContent = content.toString();
	                        goProcess = true;
	                    }
        		    }
        	    }
            }
            if( goProcess )
        	    processContent(messageContent, mailTo, subject);
        }
        // disconnect
        folderInbox.close(false);
        store.close();
	}
	
	public void processContent(String content, String mailTo, String subject) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		int spos = content.indexOf("N--");
		int epos = content.indexOf("--N");
		if( spos != -1 && epos != -1 ){
			String strEmailId = content.substring(spos+3, epos);
			BOSendEmail email = updateEmailStatus(Long.parseLong(strEmailId), SendEmailStatus.READ);
			BOSendEmailLog log = new BOSendEmailLog();
			log.setMailTo(mailTo);
			log.setStatus(SendEmailStatus.READ);
			log.setSubject(subject);
			//log.setTime(new Date());
			log.setNamaRekening(email.getNamaRekening());
			log.setCifKey(email.getCifKey());
			dao.create(log);
		}
	}
	
	public void startEmailRetention() {
		dao.startEmailRetention();
	}
}
