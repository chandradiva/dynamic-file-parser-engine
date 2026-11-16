package com.optima.oraclejdbc.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.optima.oraclejdbc.annotations.NISPEntityDateFomat;
import com.optima.oraclejdbc.annotations.NISPEntityNumberFormat;
import com.optima.oraclejdbc.annotations.NISPEntityReverse;
import com.optima.oraclejdbc.annotations.Position;

public abstract class NISPReverse<T> {

	public abstract List<String> reverse(List<T> datas, String filename);
	public abstract List<String> reverseObj(Object[] datas, List<String> alamats, String filename);
	
	protected <G> String reverse(T item, Class<G> c) {
		String result = "";
		String temp = "";
		Class<?>[] noparam = new Class<?>[0];
		
		try {
			Field[] listField = null;
			List<Field> allField = null;
			if (c.getSuperclass() != null) {
				listField = c.getSuperclass().getDeclaredFields();
				allField = new ArrayList<Field>(Arrays.asList(listField));
			}
			
			if (allField != null) {
				allField.addAll(Arrays.asList(c.getDeclaredFields()));
			} else {
				allField = Arrays.asList(c.getDeclaredFields());
			}
			
			for (Field field : allField) {
				if (field.isAnnotationPresent(Position.class)) {
					boolean include = true;
					if (field.isAnnotationPresent(NISPEntityReverse.class)) {
						Annotation annRev = field.getAnnotation(NISPEntityReverse.class);
						NISPEntityReverse rev = (NISPEntityReverse) annRev;
						
						include = rev.include();
					}
					
					if (include) {
						Annotation ann = field.getAnnotation(Position.class);
						Position posAnn = (Position) ann;
						
						int length = posAnn.length();
						
						try {
							String fieldName = field.getName();
							String getter = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
							Method method = c.getMethod(getter, noparam);
							String fieldType = field.getType().getSimpleName();

							if (fieldType.equals("int") || fieldType.equals("Integer")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							} else if (fieldType.equals("String")) {
								if (!String.valueOf(method.invoke(item)).equalsIgnoreCase("null")) {
									temp = String.valueOf(method.invoke(item));
									result += temp.concat(paddingSpace(length, temp.length()));
								} else {
									result += paddingSpace(length, 0);
								}
							} else if (fieldType.equals("Date")) {
								String format = "";
								if (field.isAnnotationPresent(NISPEntityDateFomat.class)) {
									ann = field.getAnnotation(NISPEntityDateFomat.class);
									format = ((NISPEntityDateFomat) ann).format();
								} else {
									format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
								}
								
								if (!String.valueOf(method.invoke(item)).equalsIgnoreCase("null")) {
									Date dateValue = null;
									DateFormat df = new SimpleDateFormat(format);
									dateValue = df.parse(String.valueOf(method.invoke(item)));
									if (dateValue != null) {
										temp = String.valueOf(df.format(dateValue));
										result += paddingSpace(length, temp.length()) + temp;
//										result += String.valueOf(df.format(dateValue));
									} else {
										result += paddingSpace(length, 0);
									}
								} else {
									result += paddingSpace(length, 0);
								}
							} else if (fieldType.equalsIgnoreCase("long")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							} else if (fieldType.equalsIgnoreCase("double")) {
								int scale = 0;
								if (field.isAnnotationPresent(NISPEntityNumberFormat.class)) {
									ann = field.getAnnotation(NISPEntityNumberFormat.class);
									scale = ((NISPEntityNumberFormat) ann).scale();
								} else {
									scale = 0;
								}
								
								int dividen = getDividen(scale);
								
								Locale lcl = Locale.ENGLISH;
								NumberFormat nf = NumberFormat.getInstance(lcl);
								
								Double doubleVal = Double.parseDouble(String.valueOf(method.invoke(item)));
								if (doubleVal != null) {
									doubleVal = doubleVal * dividen;
									temp = nf.format(doubleVal);
									temp = temp.replaceAll(",", "");
									result += paddingSpace(length, temp.length()).concat(temp); //before: result += paddingSpace(length, temp.length()).concat(temp); 
								} else {
									result += paddingSpace(length, 3).concat(".00"); //before: result += paddingZero(length, 0)
								}
							} else if (fieldType.equalsIgnoreCase("float")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected <G> String reverseObj(Object item, Class<G> c) {
		String result = "";
		String temp = "";
		Class<?>[] noparam = new Class<?>[0];
		
		try {
			Field[] listField = null;
			List<Field> allField = null;
			if (c.getSuperclass() != null) {
				listField = c.getSuperclass().getDeclaredFields();
				allField = new ArrayList<Field>(Arrays.asList(listField));
			}
			
			if (allField != null) {
				allField.addAll(Arrays.asList(c.getDeclaredFields()));
			} else {
				allField = Arrays.asList(c.getDeclaredFields());
			}
			
			for (Field field : allField) {
				if (field.isAnnotationPresent(Position.class)) {
					boolean include = true;
					if (field.isAnnotationPresent(NISPEntityReverse.class)) {
						Annotation annRev = field.getAnnotation(NISPEntityReverse.class);
						NISPEntityReverse rev = (NISPEntityReverse) annRev;
						
						include = rev.include();
					}
					
					if (include) {
						Annotation ann = field.getAnnotation(Position.class);
						Position posAnn = (Position) ann;
						
						int length = posAnn.length();
						
						try {
							String fieldName = field.getName();
							String getter = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
							Method method = c.getMethod(getter, noparam);
							String fieldType = field.getType().getSimpleName();
							
							if (fieldType.equals("int") || fieldType.equals("Integer")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							} else if (fieldType.equals("String")) {
								if (!String.valueOf(method.invoke(item)).equalsIgnoreCase("null")) {
									temp = String.valueOf(method.invoke(item));
									result += temp.concat(paddingSpace(length, temp.length()));
								} else {
									result += paddingSpace(length, 0);
								}
							} else if (fieldType.equals("Date")) {
								String format = "";
								if (field.isAnnotationPresent(NISPEntityDateFomat.class)) {
									ann = field.getAnnotation(NISPEntityDateFomat.class);
									format = ((NISPEntityDateFomat) ann).format();
								} else {
									format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
								}
								
								if (!String.valueOf(method.invoke(item)).equalsIgnoreCase("null")) {
									Date dateValue = null;
									DateFormat df = new SimpleDateFormat(format);
									dateValue = df.parse(String.valueOf(method.invoke(item)));
									if (dateValue != null) {
										temp = String.valueOf(df.format(dateValue));
										result += paddingSpace(length, temp.length()) + temp;
//										result += String.valueOf(df.format(dateValue));
									} else {
										result += paddingSpace(length, 0);
									}
								} else {
									result += paddingSpace(length, 0);
								}
							} else if (fieldType.equalsIgnoreCase("long")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							} else if (fieldType.equalsIgnoreCase("double")) {
								int scale = 0;
								if (field.isAnnotationPresent(NISPEntityNumberFormat.class)) {
									ann = field.getAnnotation(NISPEntityNumberFormat.class);
									scale = ((NISPEntityNumberFormat) ann).scale();
								} else {
									scale = 0;
								}
								
								int dividen = getDividen(scale);
								
								Locale lcl = Locale.ENGLISH;
								NumberFormat nf = NumberFormat.getInstance(lcl);
								
								Double doubleVal = Double.parseDouble(String.valueOf(method.invoke(item)));
								if (doubleVal != null) {
									doubleVal = doubleVal * dividen;
									temp = nf.format(doubleVal);
									temp = temp.replaceAll(",", "");
									result += paddingSpace(length, temp.length()).concat(temp); //before: result += paddingSpace(length, temp.length()).concat(temp); 
								} else {
									result += paddingSpace(length, 3).concat(".00"); //before: result += paddingZero(length, 0)
								}
							} else if (fieldType.equalsIgnoreCase("float")) {
								temp = String.valueOf(method.invoke(item));
								result += paddingSpace(length, temp.length()) + temp;
//								result += temp.concat(paddingSpace(length, temp.length()));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private String paddingSpace(int length, int lengthStr) {
		String result = "";
		
		int diff = length - lengthStr;
		for (int i = 0; i < diff; i++) {
			result += " ";
		}
		
		return result;
	}
	
	private int getDividen(int scale) {
		String div = "1";
		for (int i = 0; i < scale; i++) {
			div += "0";
		}
		
		return Integer.parseInt(div);
	}
	
	private String paddingZero(int length, int lengthStr) {
		String result = "";
		
		int diff = length - lengthStr;
		for (int i = 0; i < diff; i++) {
			result += "0";
		}
		
		return result;
	}
}
