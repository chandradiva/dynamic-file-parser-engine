package com.optima.oraclejdbc.jsonserializer;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleSerializer extends JsonSerializer<Double>{

	@Override
	public void serialize(Double value, JsonGenerator jg, SerializerProvider sp)
			throws IOException, JsonProcessingException {
		
		if( value == null )
			jg.writeNull();
		else{
			DecimalFormat df = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.US));
			jg.writeNumber(df.format(value));
		}
	}

}
