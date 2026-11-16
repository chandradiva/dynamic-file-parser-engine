package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.DailyStatement;

@Service
public class DailyStatementReverse extends NISPReverse<DailyStatement> {

	@Override
	public List<String> reverse(List<DailyStatement> datas, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (DailyStatement item : datas) {
				String ds = reverse(item, DailyStatement.class);
				
				rd.add(ds);
			}
			
			return rd;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<String> reverseObj(Object[] datas, List<String> alamats,
			String filename) {
		// TODO Auto-generated method stub
		return null;
	}
}
