package com.optima.oraclejdbc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;

import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityDefaultValue;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.Position;
import com.optima.oraclejdbc.daoimpl.ParentDao;
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
import com.optima.oraclejdbc.utils.FileUtility;
import com.optima.oraclejdbc.utils.MailProcessing;

public abstract class NISPParser<T> {
	private static final Logger logger = Logger.getLogger(NISPParser.class);

	private static Object processSync = new Object();
	protected static Object fileSync = new Object();

	
	//protected final Class<T> persistentClass;
	
//	@SuppressWarnings("unchecked")
//	public NISPParser() {
//		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//	}

//	public Class<T> getObj() {
//		return this.persistentClass;
//	}
	
	@Autowired
	ParentDao<T> dao;
	
	@Autowired
	private MailProcessing mailProcessing;
	
	@Resource(name="confProp")
	private Properties confProperties;
	
	@Autowired
	SystemParameterDao sysParamDao;
	
	protected List<String> unzip(String filename) throws IOException{
		File inputFile = null;
		ArrayList<String> res = new ArrayList<String>();
		SevenZFile zipFile = null;
		try {
			inputFile = new File(filename);
			if( !inputFile.exists() )
				return null;
			String dir = inputFile.getParent();
			Tika tika = new Tika();
			String mediaType = tika.detect(inputFile);
			System.out.println("Media Type: "+mediaType);
			if( mediaType.equalsIgnoreCase("application/x-7z-compressed") ){
				zipFile = new SevenZFile(inputFile);
				SevenZArchiveEntry entry = zipFile.getNextEntry();
				while( entry != null ){
					if( entry.isDirectory() ){
						entry = zipFile.getNextEntry();
						continue;
					}
					
					File entryFile = new File(dir, entry.getName());
					FileOutputStream fos = new FileOutputStream(entryFile);
					byte[] content = new byte[1024*1024];
					int read = 0;
					while( (read = zipFile.read(content)) != -1 ){
						fos.write(content, 0, read);
					}
					fos.close();
					res.add(entryFile.getAbsolutePath());	
					entry = zipFile.getNextEntry();
				}
			}			
		} finally{
			if( zipFile != null )
				try {
					zipFile.close();
				} catch (IOException e) {
					System.out.println("You are dooomed, very very doomed");
					e.printStackTrace();
				}
		}
		return res;
	}
	
	protected void processData(String inputFilename, List<String> datas, final boolean success[], List<String> listLine, int startNumber){
		synchronized (processSync) {
			final List<T> models = extract(datas, success, listLine, inputFilename, startNumber);
		    if (models != null) {
		    	DBThreadFactory thread = DBThreadFactory.intances();
			    thread.newThread(new Runnable() {
				
					@Override
					public void run() {
						try {
							saveToDbBatch(models);
						} catch (Exception e) {
							logger.error("Error", e);
						}
					}
				});	    		    	
		    } else {
		    	//failed
		    	success[0] = false;
				// FileUtility.moveFileToFailed(child.toString());
		    }
		}
	}
	
	public void setFilename(String inputFilename) throws Exception {
		List<String> entries = unzip(inputFilename);
		if( entries == null )
			return;		
		synchronized (fileSync) {			
			List<String> datas = new ArrayList<String>();
			List<String> listLine = new ArrayList<String>();
			boolean success[] = new boolean[1];
			success[0] = true;
			try{
				for (String filename : entries) {
					boolean canAccess = false;
					int startNumber = 0;
					do{
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(filename);
							byte[] buffer = new byte[10*1024*1024];
							int read;
							String unfinished = "";
							
							while( (read=fis.read(buffer)) != -1 ){
								System.out.println("Read: "+read);
								
								String allString = new String(buffer, "UTF-8");
								String[] lines = allString.split("\\r?\\n");
								lines[0] = unfinished + lines[0];
								int end = lines.length-1;
								unfinished = lines[end];
								end--;
								for(int i=0; i<end;i++)
									datas.add(lines[i]);
								if( datas.size() >= 10000 ){
									System.out.println("data size: "+datas.size());
									List<String> finalData = new ArrayList<String>();
									finalData.addAll(datas);
									processData(inputFilename,finalData, success, listLine, startNumber);
									startNumber+=datas.size();
									datas.clear();																		
								}									
							}
							
							if( unfinished.length() > 0 )
								datas.add(unfinished);
							List<String> finalData = new ArrayList<String>();
							finalData.addAll(datas);
							processData(inputFilename,finalData, success, listLine, startNumber);
							synchronized (DBThreadFactory.intances().getSyncObject()) {
								DBThreadFactory.intances().getSyncObject().wait();
							}
							System.out.println(new Date().getTime());
							canAccess=true;
						} catch (Exception e) {
							if( e instanceof FileNotFoundException && e.getMessage()!=null && e.getMessage().contains("being used by another process") ){
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									throw e1;
								}
							}else{
								canAccess=true;
								throw e;							
							}
						} finally {
							if (fis != null) {
								fis.close();
								File delete = new File(filename);
								if( delete.exists() )
									delete.delete();
								canAccess = true;
							}
							startNumber+=10000;
						}
					} while (!canAccess);
				}
			}finally{
				if (success[0]) {
		        	FileUtility.moveFileToSuccess(inputFilename);
		    	} else {
		    		FileUtility.moveFileToFailedPartial(inputFilename, listLine, mailProcessing, confProperties, sysParamDao);
//		    		FileUtility.sendEmail(inputFilename, mailProcessing, confProperties, sysParamDao);
		    	}	
			}
		}		
	}
	
//	public abstract List<T> extract(List<String> datas, boolean success[]);
	public abstract List<T> extract(List<String> datas, boolean success[], List<String> listLine, String filename, int startNumber);
	
	protected <G> G extract(String item, Class<G> c) 
		throws NISPNoSuchMethodException, NISPNumberFormatException, 
		NISPInvocationTargetException, NISPIllegalAccessException, 
		NISPParseException, NISPInstantiationException, NISPValueNotValidException, NISPValueLengthNotMatchException {
		
		Class<?>[] param = new Class[1];
		
		G member = null;
		try {
			member = c.newInstance();
			boolean beforeExpectNext = false;
			for (Field field : c.getDeclaredFields()) {
				
				if (field.isAnnotationPresent(Position.class)) {
					Annotation ann = field.getAnnotation(Position.class);
					Position posAnn = (Position) ann;
					
					int start = posAnn.start();
					int end = posAnn.start() + posAnn.length();
					boolean variable = posAnn.variable();
					
					if (item.length() < end) {
						if( start >= item.length() ){
							if( beforeExpectNext ){
								beforeExpectNext = posAnn.expectNext();
								continue;								
							}else
								throw new NISPValueLengthNotMatchException();
						}else{
							if( variable )
								end = item.length();
							else
								throw new NISPValueLengthNotMatchException();
						}						
					}
					beforeExpectNext = posAnn.expectNext();
					String value = item.substring(start, end).trim();
					
					try {
						String fieldName = field.getName();
						String setter = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
						param[0] = field.getType();
						Method method = c.getDeclaredMethod(setter, param);
						String fieldType = param[0].getSimpleName();
						
						if (fieldType.equals("int") || fieldType.equals("Integer")){
							method.invoke(member, Integer.parseInt(value));
						} else if(fieldType.equals("String")){
							String defaultVal = "";
							if (field.isAnnotationPresent(NISPEntityDefaultValue.class)) {
								ann = field.getAnnotation(NISPEntityDefaultValue.class);
								defaultVal = ((NISPEntityDefaultValue) ann).defaultValue();
								
								if (defaultVal.contains(value)) {
									method.invoke(member, value);
								} else {
									throw new NISPValueNotValidException();
								}
							} else {
								method.invoke(member, value);
							}
						} else if(fieldType.equals("Date")){
							String format;
							if (field.isAnnotationPresent(NISPEntityDateFomat.class)) {
								ann = field.getAnnotation(NISPEntityDateFomat.class);
								format = ((NISPEntityDateFomat) ann).format();
							} else {
								format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
							}
							
							Date dateValue = null;
							SimpleDateFormat sdf = new SimpleDateFormat(format);
							if (value != null) {
								if (!value.equals("")) {
									if (value.equals("00000000") || value.equals("0000") || value.equals("0")) {
										sdf.setLenient(true);
										dateValue = null;
									} else {
										if (posAnn.length() - value.length() == 1 
												|| posAnn.length() - value.length() == 3) {
											value = "0" + value;
										}
										sdf.setLenient(false);
										dateValue = sdf.parse(value);
									}
								}
							}
							
							method.invoke(member, dateValue);
						} else if (fieldType.equalsIgnoreCase("long")){
							method.invoke(member, Long.parseLong(value));
						} else if (fieldType.equalsIgnoreCase("double")){
							int scale = 0;
							if (field.isAnnotationPresent(NISPEntityNumberFormat.class)) {
								ann = field.getAnnotation(NISPEntityNumberFormat.class);
								scale = ((NISPEntityNumberFormat) ann).scale();
							} else {
								scale = 0;
							}
							
							int dashPos = value.indexOf("-");
							if (dashPos >= 0) {
								value = value.substring(dashPos);
							}
							if (scale == 2) {
								method.invoke(member, Double.parseDouble(value) / 100);
							} else if (scale == 4) {
								method.invoke(member, Double.parseDouble(value) / 10000);
							} else {
								method.invoke(member, Double.parseDouble(value));
							}
						} else if (fieldType.equalsIgnoreCase("float")){
							method.invoke(member, Float.parseFloat(value));
						}							
					} catch (NoSuchMethodException e) {
						throw new NISPNoSuchMethodException();
					} catch (NumberFormatException e) {
						throw new NISPNumberFormatException();
					} catch (InvocationTargetException e) {
						throw new NISPInstantiationException();
					} catch (IllegalAccessException e) {
						throw new NISPIllegalAccessException();
					} catch (ParseException e) {
						throw new NISPParseException("", 0);
					} catch (NISPValueNotValidException e) {
						throw new NISPValueNotValidException();
					}
				}
			}			
		} catch (InstantiationException e) {
			throw new NISPInstantiationException();
		} catch (IllegalAccessException e) {
			throw new NISPIllegalAccessException();
		} catch (NoSuchMethodException e) {
			throw e;
		} catch (SecurityException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		}
		
		return member;
	}
	
	public void saveToDb(List<T> models) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for (T model : models) {
			dao.insertOrUpdate(model);
		}
	}
	
	public void saveToDbBatch(List<T> models) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		dao.insertOrUpdateBatch(models.toArray());
	}
}