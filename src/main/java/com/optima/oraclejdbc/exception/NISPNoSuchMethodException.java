package com.optima.oraclejdbc.exception;

public class NISPNoSuchMethodException extends NoSuchMethodException {

	private static final long serialVersionUID = -5177262816439555633L;

	@Override
	public String getMessage() {
		return "00000001";
	}

}
