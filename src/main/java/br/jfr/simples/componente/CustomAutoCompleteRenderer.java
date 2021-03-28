package br.jfr.simples.componente;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.autocomplete.AutoCompleteRenderer;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;

public class CustomAutoCompleteRenderer extends AutoCompleteRenderer {
	
    private static final String CUSTOM_CLASSES = "form-control";
    
    @Override
    protected void encodeInput(FacesContext context, AutoComplete ac, String clientId) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        boolean disabled = ac.isDisabled();
        String var = ac.getVar();
        String itemLabel;
        String defaultStyleClass = ac.isDropdown() ? AutoComplete.INPUT_WITH_DROPDOWN_CLASS : AutoComplete.INPUT_CLASS;
        String styleClass = disabled ? defaultStyleClass + " ui-state-disabled" : defaultStyleClass;
        styleClass = ac.isValid() ? styleClass : styleClass + " ui-state-error";
        String inputStyle = ac.getInputStyle();
        String inputStyleClass = ac.getInputStyleClass();
        inputStyleClass = (inputStyleClass == null) ? styleClass : styleClass + " " + inputStyleClass;
        String autocompleteProp = (ac.getAutocomplete() != null) ? ac.getAutocomplete() : "off";

        writer.startElement("input", null);
        writer.writeAttribute("id", clientId + "_input", null);
        writer.writeAttribute("name", clientId + "_input", null);
        writer.writeAttribute("type", ac.getType(), null);
        
        inputStyleClass += " "+CUSTOM_CLASSES;
        writer.writeAttribute("class", inputStyleClass, null);
        
        writer.writeAttribute("autocomplete", autocompleteProp, null);

        if (inputStyle != null) {
            writer.writeAttribute("style", inputStyle, null);
        }

        renderAccessibilityAttributes(context, ac);
        renderPassThruAttributes(context, ac, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, ac, HTML.INPUT_TEXT_EVENTS);

        if (var == null) {
            itemLabel = ComponentUtils.getValueToRender(context, ac);

            if (itemLabel != null) {
                writer.writeAttribute("value", itemLabel, null);
            }
        }
        else {
            Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

            if (ac.isValid()) {
                requestMap.put(var, ac.getValue());
                itemLabel = ac.getItemLabel();
            }
            else {
                Object submittedValue = ac.getSubmittedValue();

                Object value = ac.getValue();

                if (submittedValue == null && value != null) {
                    requestMap.put(var, value);
                    itemLabel = ac.getItemLabel();
                }
                else if (submittedValue != null) {
                    // retrieve the actual item (pojo) from the converter
                    try {
                        Object item = getConvertedValue(context, ac, String.valueOf(submittedValue));
                        requestMap.put(var, item);
                        itemLabel = ac.getItemLabel();
                    }
                    catch (ConverterException ce) {
                        itemLabel = String.valueOf(submittedValue);
                    }

                }
                else {
                    itemLabel = null;
                }

            }

            if (itemLabel != null) {
                writer.writeAttribute("value", itemLabel, null);
            }

            requestMap.remove(var);
        }

        renderValidationMetadata(context, ac);

        writer.endElement("input");
    }
    
}
