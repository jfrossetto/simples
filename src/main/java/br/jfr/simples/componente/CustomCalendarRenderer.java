package br.jfr.simples.componente;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.HTML;

public class CustomCalendarRenderer extends CalendarRenderer {
	
    private static final String CUSTOM_CLASSES = "form-control";

	@Override
    protected void encodeInput(FacesContext context, Calendar calendar, String id, String value, boolean popup) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String type = popup ? calendar.getType() : "hidden";
        String labelledBy = calendar.getLabelledBy();
        String inputStyle = calendar.getInputStyle();
        String inputStyleClass = calendar.getInputStyleClass();

        writer.startElement("input", null);
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("name", id, null);
        writer.writeAttribute("type", type, null);

        if (calendar.isRequired()) {
            writer.writeAttribute("aria-required", "true", null);
        }

        if (!isValueBlank(value)) {
            writer.writeAttribute("value", value, null);
        }

        if (popup) {
            inputStyleClass = (inputStyleClass == null) ? Calendar.INPUT_STYLE_CLASS
                    : Calendar.INPUT_STYLE_CLASS + " " + inputStyleClass;
            if (calendar.isDisabled()) {
                inputStyleClass = inputStyleClass + " ui-state-disabled";
            }
            if (!calendar.isValid()) {
                inputStyleClass = inputStyleClass + " ui-state-error";
            }
            
            inputStyleClass += " "+CUSTOM_CLASSES;
            writer.writeAttribute("class", inputStyleClass, null);

            if (inputStyle != null) {
                writer.writeAttribute("style", inputStyle, null);
            }
            if (calendar.isReadonly() || calendar.isReadonlyInput()) {
                writer.writeAttribute("readonly", "readonly", null);
            }
            if (calendar.isDisabled()) {
                writer.writeAttribute("disabled", "disabled", null);
            }

            renderPassThruAttributes(context, calendar, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
            renderDomEvents(context, calendar, HTML.INPUT_TEXT_EVENTS);
        }

        if (labelledBy != null) {
            writer.writeAttribute("aria-labelledby", labelledBy, null);
        }

        if (RequestContext.getCurrentInstance(context).getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, calendar);
        }

        writer.endElement("input");
    }
	

}
