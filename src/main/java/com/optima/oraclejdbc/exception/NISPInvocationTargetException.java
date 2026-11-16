package com.optima.oraclejdbc.exception;

import java.lang.reflect.InvocationTargetException;

public class NISPInvocationTargetException extends InvocationTargetException {

	private static final long serialVersionUID = 9109610731433655392L;

	@Override
	public String getMessage() {
		return "00000003";
	}
	
}
