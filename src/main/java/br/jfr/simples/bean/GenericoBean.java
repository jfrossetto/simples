package br.jfr.simples.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
//import org.primefaces.component.messages.Messages;
//import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

public abstract class GenericoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ViewState viewState;

	@Inject
    protected Logger logger;
	    
    @Inject
    private FacesContext facesContext;
    //@Inject
    //private RequestContext requestContext;
    @Inject
    private transient PrimeFaces primeContext;

    public ViewState getViewState() {
		return viewState;
	}

	/**
     * @return o nome do componente default de mensagens da view
     */
    public String getDefaultMessagesComponentId() {
        return "messages"; // "growl"; //
    }
    
    /**
     * Caso o nome do componente default de mensagens tenha sido setado, este
     * metodo invocado apos adicionar mensagens faz com que ele seja atualizado
     * automaticamente
     */
    private void updateDefaultMessages() {
        if (this.getDefaultMessagesComponentId() != null 
                && !this.getDefaultMessagesComponentId().isEmpty()) {
            this.temporizeHiding(this.getDefaultMessagesComponentId());
        }
    }
    
    /**
     * Atualiza um componente pelo seu id no contexto atual
     *
     * @param componentId o id do componente
     */
    protected void updateComponent(String componentId) {
        //this.requestContext.update(componentId);
        primeContext.ajax().update(componentId);
    }

    /**
     * Executa um JavaScript na pagina pelo FacesContext atual
     *
     * @param script o script a ser executado
     */
    protected void executeScript(String script) {
        //this.requestContext.execute(script);
    	primeContext.executeScript(script);
    }

    /**
     * Apenas abre uma dialog pelo seu widgetvar
     * 
     * @param widgetVar o widgetvar para abri-la
     */
    protected void openDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').show()");
    }
    
    /**
     * Dado o id de um dialog, atualiza a mesma e depois abre pelo widgetvar
     * 
     * @param id o id da dialog para atualiza-la
     * @param widgetVar o widgetvar para abri-la
     */
    protected void updateAndOpenDialog(String id, String widgetVar) {
        this.updateComponent(id);
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * Fecha uma dialog aberta previamente
     *
     * @param widgetVar o widgetvar da dialog
     */
    protected void closeDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').hide()");
    }

    /**
     * Dado um componente, atualiza o mesmo e depois temporiza o seu fechamento
     * 
     * @param componentId o id do componente
     */
    protected void temporizeHiding(String componentId) {
        this.updateComponent(componentId);
        this.executeScript("setTimeout(\"$(\'#" + componentId + "\').slideUp(300)\", 5000)");
    }

    /**
     * Redireciona o usuario para um determinada URL, caso haja um erro, loga 
     * 
     * @param url a url para o cara ser redirecionado
     */
    protected void redirectTo(String url) {
        try {
            this.facesContext.getExternalContext().redirect(url);
        } catch (Exception ex) {
            throw new RuntimeException(
                    String.format("Can't redirect to url [%s]", url));
        }
    }
    
    public boolean containsException(Class<? extends Exception> exception, Throwable stack) {

        // se nao tem stack nao ha o que fazer!
        if (stack == null) return false;
        
        // navegamos recursivamente na stack
        if (stack.getClass().isAssignableFrom(exception)) {
            return true;
        } else {
            return this.containsException(exception, stack.getCause());
        }
    }

    /**
     * Adiciona uma mensagem de informacao na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    public void addInfo(boolean updateDefault, String message, Object... parameters) {
    	Messages.addInfo(null,message,parameters);
    	//this.facesContext.addMessage( this.getDefaultMessagesComponentId() , new FacesMessage(message) );
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * Adiciona uma mensagem de erro na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    public void addError(boolean updateDefault, String message, Object... parameters) {
    	Messages.addError(null, message, parameters);
    	//this.facesContext.addMessage( this.getDefaultMessagesComponentId() , new FacesMessage(message) );
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * Adiciona uma mensagem de aviso na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    public void addWarning(boolean updateDefault, String message, Object... parameters) {
    	Messages.addWarn(null, message, parameters);
    	//this.facesContext.addMessage( this.getDefaultMessagesComponentId() ,new FacesMessage(message) );
        if (updateDefault) this.updateDefaultMessages();
    }
    
    public String getTituloView() {
    	return "generico";
    }
    
    public String getDetalheTituloView() {
    	return "";
    }
    
    public void updatePanelsConteudo() {
    	updateComponent("panel_conteudo");
    	updateComponent("panel_header_conteudo");
    }

    /**
     * Enum para controle do estado de execucao da tela
     */
    protected enum ViewState {
        ADDING,
        LISTING,
        INSERTING,
        EDITING,
        DELETING,
        DETAILING;
    }
    
	    
}
