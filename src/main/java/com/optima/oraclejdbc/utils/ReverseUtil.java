package com.optima.oraclejdbc.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import com.optima.oraclejdbc.cons.CommonCons;

public class ReverseUtil {

	public static void writeToFileES(String reverseDir, List<String> listResult, int type, String periode) {
		
		try {
    		String folder = "";
    		String separator = File.separator;
    		if (type == 1) {
    			//giro
    			folder = "giro";
    		} else if (type == 2) {
    			//mcy
    			folder = "mcy";
    		} else if (type == 3) {
    			//mcb
    			folder = "mcb";
    		}
    		
    		String esDir = reverseDir + "eStatement" + separator + folder;
    		File dir = new File(esDir);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		String periodeDir = esDir + separator + periode;
    		dir = new File(periodeDir);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		String filename = folder + "_" + periode + ".txt";
    		String fileFullpath = periodeDir + separator + filename;
    		File fileReverse = new File(fileFullpath);
    		if (!fileReverse.exists()) {
    			fileReverse.createNewFile();
    		}
    		
    		FileWriter fw = new FileWriter(fileReverse.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String item : listResult) {
				bw.write(item);
				bw.newLine();
			}
			bw.close();
    		   
    	    System.out.println("Write to file successfully!");
    	    
    	} catch(IOException e){
    	    e.printStackTrace();
    	}
	}
	
	public static void writeToFileCC(String reverseDir, List<String> listResult, String periode) {
		
		try {
    		String separator = File.separator;
    		
    		String periodeDir = reverseDir + "CC" + separator + periode;
    		File dir = new File(periodeDir);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		String filename = "CC_" + periode + ".txt";
    		String fileFullpath = periodeDir + separator + filename;
    		File fileReverse = new File(fileFullpath);
    		if (!fileReverse.exists()) {
    			fileReverse.createNewFile();
    		}
    		
    		FileWriter fw = new FileWriter(fileReverse.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String item : listResult) {
				bw.write(item);
				bw.newLine();
			}
			bw.close();
    		   
    	    System.out.println("Write to file successfully!");
    	    
    	} catch(IOException e){
    	    e.printStackTrace();
    	}
	}
	
	public static void writeToFileESBatch(ZipOutputStream zs, String password, List<String> listResult, int type, String batch, String createdDate) {
		
		try {
    		String typeStr = "";
    		if (type == 1) {
    			//giro
    			typeStr = "DDI236TXTH";
    		} else if (type == 2) {
    			//mcy
    			typeStr = "DDI236MTXH";
    		} else if (type == 3) {
    			//mcb
    			typeStr = "DDI236MBLH";
    		}
    		
    		String filename = typeStr + "_" + createdDate + "_" + batch + ".txt";
    		
			//zip
			ZipParameters zipParam = new ZipParameters();
			
			zipParam.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParam.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			
			zipParam.setEncryptFiles(true);
			zipParam.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//			zipParam.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_128);
			zipParam.setPassword(password);
			
			zipParam.setFileNameInZip(filename);
			zipParam.setSourceExternalStream(true);
			zs.putNextEntry(null, zipParam);
			
			for (String item : listResult) {
				item += "\n";
				zs.write(item.toString().getBytes());
			}
			
			zs.closeEntry();
    		   
    	    System.out.println("Write to file successfully!");
    	    
    	} catch(IOException e){
    	    e.printStackTrace();
    	} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFileCCBatch(String reverseDir, List<String> listResult, String batch) {
		
		try {
    		String separator = File.separator;
    		
    		String periodeDir = reverseDir + "CC" + separator + batch;
    		File dir = new File(periodeDir);
    		if (!dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		String filename = "CC_" + batch + ".txt";
    		String fileFullpath = periodeDir + separator + filename;
    		File fileReverse = new File(fileFullpath);
    		if (!fileReverse.exists()) {
    			fileReverse.createNewFile();
    		}
    		
    		FileWriter fw = new FileWriter(fileReverse.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String item : listResult) {
				bw.write(item);
				bw.newLine();
			}
			bw.close();
    		   
    	    System.out.println("Write to file successfully!");
    	    
    	} catch(IOException e){
    	    e.printStackTrace();
    	}
	}
}