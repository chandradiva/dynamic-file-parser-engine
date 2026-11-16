package com.optima.oraclejdbc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Position {

	int start() default 0;
	int length();
	boolean variable() default false;
	boolean expectNext() default false;
}
