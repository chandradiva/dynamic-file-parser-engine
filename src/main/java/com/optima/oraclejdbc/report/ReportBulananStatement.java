package com.optima.oraclejdbc.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.optima.oraclejdbc.elixirprop.ESProp;
import com.optima.oraclejdbc.model.ElixirModel;
import com.optima.oraclejdbc.model.estatement.ESGiro;
import com.optima.oraclejdbc.model.estatement.ESGiroHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBHistory;
import com.optima.oraclejdbc.model.estatement.ESMCBilingual;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrencyHistory;
import com.optima.oraclejdbc.model.estatement.ViewESAccountInfo;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterHistoryReport;
import com.optima.oraclejdbc.model.estatement.ViewESGiroFooterReport;

public class ReportBulananStatement {

	public static String generateReportMCY(ElixirModel elModel, ESMultiCurrency mcy, 
			ViewESAccountInfo info, Date tglCetak, String filename, String password, OutputStream os, Double totalIdr) {
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESMultiCurrency.rml";
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			File file = new File(filename);
			
			Properties prop = ESProp.getEsMcyProperties(mcy, info, tglCetak, totalIdr, elModel.getProviderUrl());
			
			fos = new FileOutputStream(file);
			ersClient.renderReport(reportName, contentType, fos, prop);
			
			PDDocument doc = PDDocument.load(file);
			
			if( password != null ){
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null )
				doc.save(os);
			else
				doc.save(file);
			doc.close();
			
			return file.getPath();
		} catch (NetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ersClient.close();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}
		
		return "";
	}

	public static String generateReportGiro(ElixirModel elModel, ESGiro giro, int type, ViewESAccountInfo info, 
			Date tglCetak, ViewESGiroFooterReport footer, String filename, String password, OutputStream os) {
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESGiro.rml";
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			File file = new File(filename);
			
			Properties prop = ESProp.getEsGiroProperties(giro, info, tglCetak, footer, type, elModel.getProviderUrl());
			
			fos = new FileOutputStream(file);
			ersClient.renderReport(reportName, contentType, fos, prop);
			
			PDDocument doc = PDDocument.load(file);
			if( password != null ){
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null )
				doc.save(os);
			else
				doc.save(file);
			doc.close();
			
			return file.getPath();
		} catch (NetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ersClient.close();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}
		
		return "";
	}
	
	public static String generateReportMCB(ElixirModel elModel, ESMCBilingual mcb, 
			ViewESAccountInfo info, Date tglCetak, String filename, String password, OutputStream os, Double totalIdr) {
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESMCBilingual.rml"; //nanti diganti ke mcb
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			File file = new File(filename);
			
			Properties prop = ESProp.getEsMcbProperties(mcb, info, tglCetak, totalIdr, elModel.getProviderUrl());
			
			fos = new FileOutputStream(file);
			ersClient.renderReport(reportName, contentType, fos, prop);
			
			PDDocument doc = PDDocument.load(file);
			
			if( password != null ){
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null )
				doc.save(os);
			else
				doc.save(file);
			doc.close();
			
			return file.getPath();
		} catch (NetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ersClient.close();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}
		
		return "";
	}
	
	public static String generateReportMCYHistory(ElixirModel elModel, ESMultiCurrencyHistory mcy, 
			String filename, String password, int isPrint, int isReprint, OutputStream os, 
			Double totalIdr, String tglCetakStr) throws NetException, IOException {
		
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESMultiCurrencyHistory.rml";
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Properties prop = ESProp.getEsMcyHistoryProperties(mcy, totalIdr, isReprint, elModel.getProviderUrl(), tglCetakStr);
			ersClient.renderReport(reportName, contentType, baos, prop);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			PDDocument doc = PDDocument.load(bais);
			if( isPrint != 1 && password != null ){
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null ){
				doc.save(os);
			}else{
				File file = new File(filename);
				fos = new FileOutputStream(file);
				doc.save(file);
			}
			doc.close();
			
			return filename;
		} finally {
			ersClient.close();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}			
	}
	
	public static String generateReportGiroHistory(ElixirModel elModel, ESGiroHistory giro,
			ViewESGiroFooterHistoryReport footer, String filename, String password, int isPrint, 
			int isReprint, OutputStream os, String tglCetakStr) throws IOException, NetException {
		
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESGiroHistory.rml";
		String contentType = "application/pdf";
		
		try {
			
			Properties prop = ESProp.getEsGiroHistoryProperties(giro, footer, isReprint, elModel.getProviderUrl(), tglCetakStr);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ersClient.renderReport(reportName, contentType, baos, prop);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			PDDocument doc = PDDocument.load(bais);
						
			if( isPrint != 1 && password != null ){
				int keyLength = 128;
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null ){
				doc.save(os);
			}else{
				File file = new File(filename);
				doc.save(file);
			}
			doc.close();
			
			return filename;
		} finally {
			ersClient.close();			
		}				
	}
	
	public static String generateReportMCBHistory(ElixirModel elModel, ESMCBHistory mcb, 
			String filename, String password, int isPrint, int isReprint, OutputStream os, 
			Double totalIdr, String tglCetakStr) throws IOException, NetException {
		
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/ESMCBilingualHistory.rml";
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Properties prop = ESProp.getEsMcbHistoryProperties(mcb, totalIdr, isReprint, elModel.getProviderUrl(), tglCetakStr);
			ersClient.renderReport(reportName, contentType, baos, prop);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			PDDocument doc = PDDocument.load(bais);
			if( isPrint != 1 && password != null ){
				int keyLength = 128;
				
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if( os != null ){
				doc.save(os);
			}else{
				File file = new File(filename);
				fos = new FileOutputStream(file);
				doc.save(file);
			}
			doc.close();
			
			return filename;
		}finally {
			ersClient.close();
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}			
	}
}
