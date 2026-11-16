package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.exception.NISPIllegalAccessException;
import com.optima.oraclejdbc.exception.NISPInstantiationException;
import com.optima.oraclejdbc.exception.NISPInvocationTargetException;
import com.optima.oraclejdbc.exception.NISPNoSuchMethodException;
import com.optima.oraclejdbc.exception.NISPNumberFormatException;
import com.optima.oraclejdbc.exception.NISPParseException;
import com.optima.oraclejdbc.exception.NISPValueLengthNotMatchException;
import com.optima.oraclejdbc.exception.NISPValueNotValidException;
import com.optima.oraclejdbc.model.estatement.ESMCBilingual;

@Service
public class ESMCBilingualParser extends NISPParser<ESMCBilingual> {

	@Override
	public List<ESMCBilingual> extract(List<String> datas, boolean[] success, List<String> listLine, String filename, int startNumber) {
		List<ESMCBilingual> rd = new ArrayList<ESMCBilingual>();
		
		try {
			int line = 1;
			Date today = new Date();
			for (String item : datas) {
				try {
					ESMCBilingual mcb = extract(item, ESMCBilingual.class);
					
					rd.add(mcb);
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
			success[0] = false;
			return null;
		}
	}

}
