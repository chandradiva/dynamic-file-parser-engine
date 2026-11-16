package com.optima.oraclejdbc.exception;

public class NISPInstantiationException extends InstantiationException {

	private static final long serialVersionUID = -4960088995229416945L;
	
	public NISPInstantiationException() {
		super();
	}

	public NISPInstantiationException(String s) {
		super(s);
	}

	@Override
	public String getMessage() {
		return "00000006";
	}

}
