package com.optima.oraclejdbc.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.support.AbstractSqlTypeValue;

import oracle.jdbc.OracleConnection;

public class SqlStringArray extends AbstractSqlTypeValue{

	private List<String> values;
	
	public SqlStringArray(List<String> values){
		this.values = values;
	}
	@Override
	protected Object createTypeValue(Connection con, int sqlType, String typeName) throws SQLException {
		OracleConnection oraCon = con.unwrap(OracleConnection.class);
		return oraCon.createARRAY(typeName, values.toArray());
	}

}
