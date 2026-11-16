package com.optima.oraclejdbc.daoimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.optima.oraclejdbc.cons.CommonCons;
import com.optima.oraclejdbc.model.MappingProduct;

@Component
public class MappingProductDao extends ParentDao<MappingProduct>{

	public MappingProduct getProductByCode(String productCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException{
		String sql = "SELECT * FROM "+CommonCons.DB_SCHEMA+".TBL_MAPPING_PRODUCT "
				+ "WHERE PRODUCT_CODE like ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(productCode.trim());
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(MappingProduct.class, rows.get(0));
		}
	}

	public MappingProduct getProductById(Long productId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		String sql = "SELECT * FROM "+CommonCons.DB_SCHEMA+".TBL_MAPPING_PRODUCT "
				+ "WHERE ID=?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(productId);
		
		List<Map<String, Object>> rows = template.queryForList(sql, args.toArray());
		if( rows.size() == 0 )
			return null;
		else{
			return serialize(MappingProduct.class, rows.get(0));
		}
	}
}
