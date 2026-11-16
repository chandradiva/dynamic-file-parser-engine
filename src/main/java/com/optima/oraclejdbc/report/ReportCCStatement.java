package com.optima.oraclejdbc.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import com.elixirtech.ers2.client.ERSClient;
import com.elixirtech.net.NetException;
import com.optima.oraclejdbc.model.ElixirModel;

public class ReportCCStatement {

	public static String generateReportCC(ElixirModel elModel, String filename, String password, Properties prop, OutputStream os) {
		
		String elHostname = elModel.getHostname();
		int elPort = elModel.getPort();
		String elUsername = elModel.getUsername();
		String elPassword = elModel.getPassword();
		ERSClient ersClient = new ERSClient(elHostname, elPort, elUsername, elPassword);
		
		String elWorkspace = elModel.getWorkspace();
		String reportName = elWorkspace + "/CCBillingStatementNew.rml";
		String contentType = "application/pdf";
		
		FileOutputStream fos = null;
		
		try {
			File file = new File(filename);
			fos = new FileOutputStream(file);
			ersClient.renderReport(reportName, contentType, fos, prop);
			
			PDDocument doc = PDDocument.load(file);
						
			if (password != null) {
				int keyLength = 128;
				AccessPermission permission = new AccessPermission();
				permission.setCanPrint(true);
				
				StandardProtectionPolicy policy = new StandardProtectionPolicy(password, password, permission);
				policy.setEncryptionKeyLength(keyLength);
				policy.setPermissions(permission);
				doc.protect(policy);
			}
			
			if (os != null) {
				doc.save(os);
			} else {
				doc.save(file);
			}
			
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
}
