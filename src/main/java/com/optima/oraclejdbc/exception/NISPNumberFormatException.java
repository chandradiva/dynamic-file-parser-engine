package com.optima.oraclejdbc.exception;

public class NISPNumberFormatException extends NumberFormatException {

	private static final long serialVersionUID = -376314631211722290L;

	public NISPNumberFormatException() {
		super();
	}

	public NISPNumberFormatException(String s) {
		super(s);
	}

	@Override
	public String getMessage() {
		return "00000002";
	}
}
