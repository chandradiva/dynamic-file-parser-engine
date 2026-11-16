package com.optima.oraclejdbc.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ocbc.schemas.soa.emf.account.elements.ThisTermDeposit;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;

public class FileUtility {

	public static String moveFileToPending(String fileChk, String fileOrigin) {
		InputStream inStream = null;
		OutputStream outStream = null;
		String destFilename = "";
		try {
    		File origin = new File(fileOrigin);
    		if( !origin.exists() )
    			return null;
    		destFilename = origin.getParentFile().getParent()+File.separator+"process"
    				+ File.separator+origin.getName();    		
    		
    		File destination = new File(destFilename);
    		
    		File chk = null;
    		
    		
    		chk = new File(fileChk);
    		
    	    inStream = new FileInputStream(origin);
    	    outStream = new FileOutputStream(destination);
        	
    	    byte[] buffer = new byte[1024];
    		
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	    	outStream.write(buffer, 0, length);
    	    }
    	 
    	    inStream.close();
    	    outStream.close();
    	    
    	    //delete the original file
    	    origin.delete();
    	    
    	    //delete chk file
    	    chk.delete();
    	    
    	    System.out.println("File move to Process successfully!");    	    
    	} catch(IOException e){
    	    e.printStackTrace();
    	}
    	return destFilename;
	}
	
	public static void moveFileToSuccess(String fileOrigin) throws IOException {
		InputStream inStream = null;
		OutputStream outStream = null;
			
		File origin = new File(fileOrigin);
		if( !origin.exists() )
			return;
		String destFilename = origin.getParentFile().getParent()+File.separator+"success"
				+ File.separator+origin.getName();    		
		
		File destination = new File(destFilename);
		
	    inStream = new FileInputStream(origin);
	    outStream = new FileOutputStream(destination);
    	
	    byte[] buffer = new byte[1024];
		
	    int length;
	    //copy the file content in bytes 
	    while ((length = inStream.read(buffer)) > 0){
	    	outStream.write(buffer, 0, length);
	    }
	 
	    inStream.close();
	    outStream.close();
	    
	    //delete the original file
	    origin.delete();
	    
	    System.out.println("File move to Success successfully!");
	}
	
	public static void moveFileToFailed(String fileOrigin) throws IOException {
		InputStream inStream = null;
		OutputStream outStream = null;
			
		File origin = new File(fileOrigin);
		if( !origin.exists() )
			return;
		String destFilename = origin.getParentFile().getParent()+File.separator+"failed"
				+ File.separator+origin.getName();    		
		
		File destination = new File(destFilename);

		inStream = new FileInputStream(origin);
	    outStream = new FileOutputStream(destination);
    	
	    byte[] buffer = new byte[1024];
		
	    int length;
	    //copy the file content in bytes 
	    while ((length = inStream.read(buffer)) > 0){
	    	outStream.write(buffer, 0, length);
	    }
	 
	    inStream.close();
	    outStream.close();
	    
	    //delete the original file
	    origin.delete();
	    
	    System.out.println("File move to Failed successfully!!!!!!!!!!!");
	}
	
	public static void moveFileToFailedPartial(String fileOrigin, List<String> listLine, 
			MailProcessing mail, Properties prop, SystemParameterDao sysDao) throws IOException {
		
		InputStream inStream = null;
		OutputStream outStream = null;
			
		File origin = new File(fileOrigin);
		if( !origin.exists() )
			return;
		String originName = origin.getName();
		String failedDir = origin.getParentFile().getParent()+File.separator+"failed"+ File.separator;
		String destFilename = failedDir+originName;    		
		File destination = new File(destFilename);
		
		String errFilename = failedDir+FilenameUtils.getBaseName(originName)+".ERR";
		File err = new File(errFilename);
		
		if (!err.exists()) {
			err.createNewFile();
		} 
		
		FileWriter fw = new FileWriter(err.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write(DateUtility.formatDate7(new Date()));
		bw.newLine();
		for (String item : listLine) {
			bw.write(item);
			bw.newLine();
		}
		bw.close();
		
	    inStream = new FileInputStream(origin);
	    outStream = new FileOutputStream(destination);
    	
	    byte[] buffer = new byte[1024];
		
	    int length;
	    //copy the file content in bytes 
	    while ((length = inStream.read(buffer)) > 0){
	    	outStream.write(buffer, 0, length);
	    }
	 
	    inStream.close();
	    outStream.close();
	    
	    //delete the original file
	    origin.delete();
	    
	    System.out.println("File move to Failed successfully!!!!");
	    
	    //send email
	    String mailFrom = CommonUtils.getMailFrom(prop);
		String mailTo = sysDao.getParameterValue("MAEANP");
		
		File fileAttach = new File(fileOrigin);
		
		Date today = new Date();
		String subject = "Parsing File Error - " + fileAttach.getName();
		String body = DateUtility.formatDate6(today) + "\n" + "Terjadi kegagalan Parsing File: " + fileAttach.getName();
		
		List<String> attachments = new ArrayList<String>();
		attachments.add(err.getAbsolutePath());
		
		System.out.println("Sending Email....");
		mail.sendEmail(mailFrom, mailTo, subject, body, attachments);
		System.out.println("Email Sent....");
	}
	
//	public static void sendEmail(String attachment, MailProcessing mail, Properties prop, SystemParameterDao sysDao) {
//		String mailFrom = CommonUtils.getMailFrom(prop);
//		String mailTo = sysDao.getParameterValue("MAEANP");
//		
//		File fileAttach = new File(attachment);
//		
//		Date today = new Date();
//		String subject = "Parsing File Error - " + fileAttach.getName();
//		String body = DateUtility.formatDate6(today) + "\n" + "Terjadi kegagalan Parsing File: " + fileAttach.getName();
//		
//		List<String> attachments = new ArrayList<String>();
//		attachments.add(attachment);
//		
//		mail.sendEmail(mailFrom, mailTo, subject, body, attachments);
//	}
}
