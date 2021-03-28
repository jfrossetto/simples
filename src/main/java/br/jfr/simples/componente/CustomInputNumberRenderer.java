package br.jfr.simples.componente;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputnumber.InputNumberRenderer;
import org.primefaces.component.inputtext.InputText;
//import org.primefaces.context.RequestContext;
import org.primefaces.util.HTML;

public class CustomInputNumberRenderer extends InputNumberRenderer {

    private static final String CUSTOM_CLASSES = "form-control";

    /* prime 6.2    
    @Override
    protected void encodeInput(FacesContext context, InputNumber inputNumber, String clientId, String valueToRender)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String inputId = clientId + "_input";

        String inputStyle = inputNumber.getInputStyle();
        String inputStyleClass = inputNumber.getInputStyleClass();

        String style = inputStyle;

        String styleClass = InputText.STYLE_CLASS;
        styleClass = inputNumber.isValid() ? styleClass : styleClass + " ui-state-error";
        styleClass = !inputNumber.isDisabled() ? styleClass : styleClass + " ui-state-disabled";
        if (!isValueBlank(inputStyleClass)) {
            styleClass += " " + inputStyleClass;
        }
        styleClass += " "+CUSTOM_CLASSES;
        
        writer.startElement("input", null);
        writer.writeAttribute("id", inputId, null);
        writer.writeAttribute("name", inputId, null);
        writer.writeAttribute("type", inputNumber.getType(), null);
        writer.writeAttribute("value", valueToRender, null);

        renderPassThruAttributes(context, inputNumber, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, inputNumber, HTML.INPUT_TEXT_EVENTS);

        if (inputNumber.isReadonly()) {
            writer.writeAttribute("readonly", "readonly", "readonly");
        }
        if (inputNumber.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", "disabled");
        }

        if (!isValueBlank(style)) {
            writer.writeAttribute("style", style, null);
        }

        writer.writeAttribute("class", styleClass, null);

        if (RequestContext.getCurrentInstance(context).getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, inputNumber);
        }

        writer.endElement("input");
    }
*/    
    
    @Override
    protected void encodeInput(FacesContext context, InputNumber inputNumber, String clientId, String valueToRender)
            throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String inputId = clientId + "_input";

        String inputStyle = inputNumber.getInputStyle();
        String inputStyleClass = inputNumber.getInputStyleClass();

        String style = inputStyle;

        String styleClass = InputText.STYLE_CLASS;
        styleClass = inputNumber.isValid() ? styleClass : styleClass + " ui-state-error";
        styleClass = !inputNumber.isDisabled() ? styleClass : styleClass + " ui-state-disabled";
        if (!isValueBlank(inputStyleClass)) {
            styleClass += " " + inputStyleClass;
        }

        writer.startElement("input", null);
        writer.writeAttribute("id", inputId, null);
        writer.writeAttribute("name", inputId, null);
        writer.writeAttribute("type", inputNumber.getType(), null);
        writer.writeAttribute("value", valueToRender, null);

        if (!isValueBlank(style)) {
            writer.writeAttribute("style", style, null);
        }
        
        styleClass += " "+CUSTOM_CLASSES;
        writer.writeAttribute("class", styleClass, null);

        renderAccessibilityAttributes(context, inputNumber);
        renderPassThruAttributes(context, inputNumber, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, inputNumber, HTML.INPUT_TEXT_EVENTS);
        renderValidationMetadata(context, inputNumber);

        writer.endElement("input");
    }

}
