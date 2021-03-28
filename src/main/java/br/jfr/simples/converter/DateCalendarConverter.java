package br.jfr.simples.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.slf4j.Logger;

@FacesConverter("dateCalendarConverter")
public class DateCalendarConverter implements Converter { 

	@Inject
    protected Logger logger;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null ;
		
		try {
			Calendar calendar = Calendar.getInstance();		
			date = sdf.parse(value);
			calendar.setTime(date);
			return calendar;
		} catch (Exception e) {
			logger.debug("converter date  getasobject"+e.getMessage());
			return null ;
		}

	}
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			Calendar cal = (Calendar) value;
			return sdf.format( cal.getTime() ) ;
		} catch (Exception e ) {
			logger.debug("converter date  getasstring"+e.getMessage());			
			return "";
		}
	}
}
