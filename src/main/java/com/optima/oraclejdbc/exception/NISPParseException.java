package com.optima.oraclejdbc.exception;

import java.text.ParseException;

public class NISPParseException extends ParseException {

	private static final long serialVersionUID = 642866304498564573L;

	public NISPParseException(String s, int errorOffset) {
		super(s, errorOffset);
	}

	@Override
	public String getMessage() {
		return "00000005";
	}

}
