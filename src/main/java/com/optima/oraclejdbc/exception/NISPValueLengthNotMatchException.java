package com.optima.oraclejdbc.exception;

public class NISPValueLengthNotMatchException extends Exception {

	private static final long serialVersionUID = 7809032079914415837L;

	@Override
	public String getMessage() {
		return "00000008";
	}
}
