package com.optima.oraclejdbc.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.optima.oraclejdbc.model.UnitTrust;

@Service
public class UnitTrustReverse extends NISPReverse<UnitTrust> {

	@Override
	public List<String> reverse(List<UnitTrust> datas, String filename) {
		List<String> rd = new ArrayList<String>();
		
		try {
			for (UnitTrust item : datas) {
				String ut = reverse(item, UnitTrust.class);
				
				rd.add(ut);
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
