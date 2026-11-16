package com.optima.oraclejdbc.exception;

public class NISPIllegalAccessException extends IllegalAccessException {

	private static final long serialVersionUID = 958870574458082956L;

	public NISPIllegalAccessException() {
		super();
	}

	public NISPIllegalAccessException(String s) {
		super(s);
	}

	@Override
	public String getMessage() {
		return "00000004";
	}

}
