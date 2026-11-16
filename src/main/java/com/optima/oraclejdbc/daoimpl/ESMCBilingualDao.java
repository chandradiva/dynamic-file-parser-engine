package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.model.estatement.ESMCBilingual;

@Component
public class ESMCBilingualDao extends ParentDao<ESMCBilingual>{

	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (dataArray.length > 0) {
			List<ESMCBilingual> listData = new ArrayList<ESMCBilingual>();
			
			for (int i=0;i<dataArray.length;i++) {
				listData.add((ESMCBilingual) dataArray[i]); 
			}
			
			super.createBatch(listData.toArray());
		}
	}
}
