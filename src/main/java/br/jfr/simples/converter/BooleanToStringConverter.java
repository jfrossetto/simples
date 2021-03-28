package br.jfr.simples.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

		
	    public String convertToDatabaseColumn(Boolean value) {        
	        return (value != null && value) ? "S" : "N";            
        }    

	    
	    public Boolean convertToEntityAttribute(String value) {
	        return "S".equals(value);
        }
}
