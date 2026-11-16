package com.optima.oraclejdbc.exception;

public class NISPValueNotValidException extends Exception {

	private static final long serialVersionUID = -7683187544816835204L;

	@Override
	public String getMessage() {
		return "00000007";
	}

}
