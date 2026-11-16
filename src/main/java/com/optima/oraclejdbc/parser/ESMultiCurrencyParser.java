package com.optima.oraclejdbc.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.daoimpl.ESMultiCurrencyDao;
import com.optima.oraclejdbc.exception.NISPIllegalAccessException;
import com.optima.oraclejdbc.exception.NISPInstantiationException;
import com.optima.oraclejdbc.exception.NISPInvocationTargetException;
import com.optima.oraclejdbc.exception.NISPNoSuchMethodException;
import com.optima.oraclejdbc.exception.NISPNumberFormatException;
import com.optima.oraclejdbc.exception.NISPParseException;
import com.optima.oraclejdbc.exception.NISPValueLengthNotMatchException;
import com.optima.oraclejdbc.exception.NISPValueNotValidException;
import com.optima.oraclejdbc.model.estatement.ESMultiCurrency;

@Service
public class ESMultiCurrencyParser extends NISPParser<ESMultiCurrency> {

	private static final Logger logger = Logger.getLogger(ESMultiCurrencyParser.class);
	
	@Autowired
	ESMultiCurrencyDao dao;
	
	@Override
	public List<ESMultiCurrency> extract(List<String> datas, boolean[] success, List<String> listLine, String filename, int startNumber) {
		List<ESMultiCurrency> rd = new ArrayList<ESMultiCurrency>();
		
		try {
			int line = 1;
			Date today = new Date();
			for (String item : datas) {
				try {
					ESMultiCurrency mcy = extract(item, ESMultiCurrency.class);
					
					rd.add(mcy);
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
			
			return rd;
		} catch (Exception e) {
			logger.error("Error", e);
			success[0] = false;
			return null;
		}
	}

	@Override
	public void saveToDbBatch(List<ESMultiCurrency> models) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		dao.createBatch(models.toArray());
	}
}
