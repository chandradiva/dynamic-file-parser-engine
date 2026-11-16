package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.backoffice.BOBlockHardcopy;
import com.optima.oraclejdbc.utils.CommonUtils;

@Component
public class BOBlockHardcopyDao extends ParentDao<BOBlockHardcopy> {

	public List<BOBlockHardcopy> getAllData() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM BO_TBL_BLOCK_HC ORDER BY ID ASC";
		
		List<Map<String, Object>> rows = template.queryForList(sql);
		List<BOBlockHardcopy> records = new ArrayList<BOBlockHardcopy>();
		
		for (Map<String, Object> row : rows) {
			BOBlockHardcopy block = serialize(BOBlockHardcopy.class, row);
			
			records.add(block);
		}
		
		return records;
	}
	
	public List<BOBlockHardcopy> getAllDataNonCC() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM BO_TBL_BLOCK_HC WHERE TYPE = 1 ORDER BY ACCOUNT_NUMBER ASC";
		
		List<Map<String, Object>> rows = template.queryForList(sql);
		List<BOBlockHardcopy> records = new ArrayList<BOBlockHardcopy>();
		
		for (Map<String, Object> row : rows) {
			BOBlockHardcopy block = serialize(BOBlockHardcopy.class, row);
			
			records.add(block);
		}
		
		return records;
	}
	
	public List<BOBlockHardcopy> getAllDataCC() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM BO_TBL_BLOCK_HC WHERE TYPE = 2 ORDER BY ACCOUNT_NUMBER ASC";
		
		List<Map<String, Object>> rows = template.queryForList(sql);
		List<BOBlockHardcopy> records = new ArrayList<BOBlockHardcopy>();
		
		for (Map<String, Object> row : rows) {
			BOBlockHardcopy block = serialize(BOBlockHardcopy.class, row);
			
			records.add(block);
		}
		
		return records;
	}

	@Override
	public void createBatch(Object[] dataArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (dataArray.length > 0) {
			List<BOBlockHardcopy> blocks = new ArrayList<BOBlockHardcopy>();
			
			for (int i=0; i<dataArray.length; i++) {
				blocks.add((BOBlockHardcopy) dataArray[i]);
			}
			
			if (blocks.size() > 0) {
				super.createBatch(blocks.toArray());
			}
		}
	}
	
	public void deleteByAccountNo(String accountNo) {
		String sql = "DELETE FROM " + CommonCons.DB_SCHEMA + ".BO_TBL_BLOCK_HC WHERE ACCOUNT_NUMBER = ?";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(CommonUtils.normalizeAccNo(accountNo));
		
		template.update(sql, args.toArray());
	}
}
