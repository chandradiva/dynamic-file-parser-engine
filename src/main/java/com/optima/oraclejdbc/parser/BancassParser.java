package com.optima.oraclejdbc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.BancassDao;
import com.optima.oraclejdbc.daoimpl.SystemParameterDao;
import com.optima.oraclejdbc.db.DBThreadFactory;
import com.optima.oraclejdbc.exception.NISPIllegalAccessException;
import com.optima.oraclejdbc.exception.NISPInstantiationException;
import com.optima.oraclejdbc.exception.NISPInvocationTargetException;
import com.optima.oraclejdbc.exception.NISPNoSuchMethodException;
import com.optima.oraclejdbc.exception.NISPNumberFormatException;
import com.optima.oraclejdbc.exception.NISPParseException;
import com.optima.oraclejdbc.exception.NISPValueLengthNotMatchException;
import com.optima.oraclejdbc.exception.NISPValueNotValidException;
import com.optima.oraclejdbc.model.BAChildModel;
import com.optima.oraclejdbc.model.BADetailBiaya;
import com.optima.oraclejdbc.model.BADetailPremi;
import com.optima.oraclejdbc.model.BADisclaimer;
import com.optima.oraclejdbc.model.BAParentModel;
import com.optima.oraclejdbc.model.BATransactionSummary;
import com.optima.oraclejdbc.model.BATransaksiLain;
import com.optima.oraclejdbc.utils.FileUtility;
import com.optima.oraclejdbc.utils.MailProcessing;

@Service
public class BancassParser extends NISPParser<BAParentModel>{
	
	@Autowired
	BancassDao dao;
	
	@Autowired
	private MailProcessing mailProcessing;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;

	@Override
	public void setFilename(String inputFilename) throws IOException {
		List<String> entries = unzip(inputFilename);
		if( entries == null )
			return;
		List<String> datas = new ArrayList<String>();
		List<String> listLine = new ArrayList<String>();
		boolean success[] = new boolean[1];
		success[0] = true;
		
		String source = "";
		File file = new File(inputFilename);
		source = file.getName();
		dao.deleteBancass(source);		
		
		for (String filename : entries) {
			BufferedReader br = null;
			boolean canAccess = false;
			int startNumber = 0;
			do{
				try {
					String currentLine;
					br = new BufferedReader(new FileReader(filename));
					
					int counter = 0;
					
					boolean DMFound = false;
					boolean finished = false;
					while ((currentLine = br.readLine()) != null) {
//						System.out.println(currentLine);
						counter++;
						if( DMFound && !currentLine.startsWith("DM") ){
							DMFound = false;
							if( counter >= 10000 )
								finished = true;							
						}else if( currentLine.startsWith("DM") ){
							DMFound = true;
						}
						if( finished ){
							List<String> finalData = new ArrayList<String>();
							finalData.addAll(datas);
							processData(inputFilename,finalData, success, listLine, startNumber);
							finished = false;
							datas.clear();
							startNumber+=counter;
							counter = 1;
							datas.add(currentLine);							
						}else{
							datas.add(currentLine);							
						}
					}
					processData(inputFilename,datas, success, listLine, startNumber);
					synchronized (DBThreadFactory.intances().getSyncObject()) {
						DBThreadFactory.intances().getSyncObject().wait();
					}
					synchronized (DBThreadFactory.intances().getSyncObject()) {
						DBThreadFactory.intances().getSyncObject().wait();
					}
					canAccess=true;
				} catch (Exception e) {
					if( e instanceof FileNotFoundException && e.getMessage().contains("being used by another process") ){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}else{
						e.printStackTrace();
					}
				} finally {
					try {
						if (br != null) {
							br.close();
							File delete = new File(filename);
							if( delete.exists() )
								delete.delete();
							canAccess = true;
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			} while (!canAccess);
		}
		
		if (listLine.size() == 0) {
        	FileUtility.moveFileToSuccess(inputFilename);
    	} else {
    		FileUtility.moveFileToFailedPartial(inputFilename, listLine, mailProcessing, confProperties, sysParamDao);
    	}				
	}
	
	@Override
	public List<BAParentModel> extract(List<String> datas, boolean[] success, List<String> listLine, String filename, int startNumber) {
		ArrayList<BAParentModel> res = new ArrayList<BAParentModel>();
		
		//success[0] = true;
		int line = 1;
		
		Long shId = null;
		boolean shExtract = false;
		String source = "";
		File file = new File(filename);
		source = file.getName();
		
		for (String item : datas) {
			try {
				if (item.length() <= 1 && line == datas.size()) {
					continue;
				}
				
				BAParentModel data = null;
				
				if (item.startsWith("SH")) {
					shExtract = false;
					data = extract(item, BATransactionSummary.class);
					
					shExtract = true;
					shId = dao.getNextId();
					((BATransactionSummary) data).setId(shId);
					((BATransactionSummary) data).setSource(source);
				} else if (item.startsWith("DP")) {
					data = extract(item, BADetailPremi.class);
					
					((BADetailPremi) data).setBaTransactionSummaryId(shId);
				} else if (item.startsWith("DB")) {
					data = extract(item, BADetailBiaya.class);
					
					((BADetailBiaya) data).setBaTransactionSummaryId(shId);
				} else if (item.startsWith("TL")) {
					data = extract(item, BATransaksiLain.class);
					
					((BATransaksiLain) data).setBaTransactionSummaryId(shId);
				} else if (item.startsWith("DM")) {
					data = extract(item, BADisclaimer.class);
					
					((BADisclaimer) data).setBaTransactionSummaryId(shId);
				}
				
				if (shExtract) {
					res.add(data);
				}
				
			} catch (NISPNoSuchMethodException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPInvocationTargetException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPIllegalAccessException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPInstantiationException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPParseException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPNumberFormatException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPValueNotValidException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			} catch (NISPValueLengthNotMatchException e) {
				success[0] = false;
				listLine.add(startNumber+line + ", error code: " + e.getMessage());
			}
			
			line++;
		}
		
		return res;
	}

	@Override
	public void saveToDbBatch(List<BAParentModel> models) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		dao.createBatch(models.toArray());
	}

	@Override
	public void saveToDb(List<BAParentModel> models) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Long id = null;
		Date today = null;
		
		for (BAParentModel model : models) {
			today = new Date();
			
			if (model instanceof BATransactionSummary) {
				((BATransactionSummary) model).setCreatedDate(today);
				((BATransactionSummary) model).setUpdatedDate(today);
				
				id = dao.insertOrUpdate(model);
				
				if (id != null) {
					((BATransactionSummary) model).setId(id);
				}
			} else if (model instanceof BAChildModel) {
				((BAChildModel) model).setBaTransactionSummaryId(id);
				((BAChildModel) model).setCreatedDate(today);
				((BAChildModel) model).setUpdatedDate(today);
				
				dao.insertOrUpdate(model);
			}
		}
	}
	
//	@Override
//	public List<Bancass> extract(List<String> datas, boolean[] success, List<String> listLine) {
//		List<Bancass> rd = new ArrayList<Bancass>();
//		
//		try {
//			success[0] = true;
//			int line = 1;
//			Date today = new Date();
//			for (String item : datas) {
//				try {
//					Bancass bs = extract(item, Bancass.class);
//					
//					bs.setUpdatedDate(today);
//					rd.add(bs);
//				} catch (NISPNoSuchMethodException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPInvocationTargetException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPIllegalAccessException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPInstantiationException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPParseException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPNumberFormatException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPValueNotValidException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPValueLengthNotMatchException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				}
//				
//				line++;
//			}
//			
//			return rd;
//		} catch (Exception e) {
//			success[0] = false;
//			return null;
//		}
//	}
//
//	@Override
//	public void saveToDb(List<BAParentModel> models) {
//		Long id = null;
//		Date today = null;
//		
//		for (BAParentModel model : models) {
//			today = new Date();
//			
//			if (model instanceof BATransactionSummary) {
//				((BATransactionSummary) model).setCreatedDate(today);
//				((BATransactionSummary) model).setUpdatedDate(today);
//				
//				id = dao.insertOrUpdate(model);
//				
//				if (id != null) {
//					((BATransactionSummary) model).setId(id);
//				}
//			} else if (model instanceof BAChildModel) {
//				((BAChildModel) model).setBaTransactionSummaryId(id);
//				((BAChildModel) model).setCreatedDate(today);
//				((BAChildModel) model).setUpdatedDate(today);
//				
//				dao.insertOrUpdate(model);
//			}
//		}
//	}
	
//	@Override
//	public List<Bancass> extract(List<String> datas, boolean[] success, List<String> listLine) {
//		List<Bancass> rd = new ArrayList<Bancass>();
//		
//		try {
////			success[0] = true;
//			int line = 1;
//			Date today = new Date();
//			for (String item : datas) {
//				try {
//					Bancass bs = extract(item, Bancass.class);
//					
//					bs.setUpdatedDate(today);
//					rd.add(bs);
//				} catch (NISPNoSuchMethodException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPInvocationTargetException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPIllegalAccessException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPInstantiationException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPParseException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPNumberFormatException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPValueNotValidException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				} catch (NISPValueLengthNotMatchException e) {
//					success[0] = false;
//					listLine.add(line + ", error code: " + e.getMessage());
//				}
//				
//				line++;
//			}
//			
//			return rd;
//		} catch (Exception e) {
//			success[0] = false;
//			return null;
//		}
//	}
}
