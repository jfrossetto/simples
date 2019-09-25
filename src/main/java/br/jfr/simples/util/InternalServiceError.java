package br.jfr.simples.util;

public class InternalServiceError extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		private Object[] parameters;

	    public Object[] getParameters() {
			return parameters;
		}

		public void setParameters(Object[] parameters) {
			this.parameters = parameters;
		}

		/**
	     * Construtor default
	     * 
	     * @param message a mensagem de erro
	     */
	    public InternalServiceError(String message) {
	        super(message);
	    }

	    /**
	     * Construtor para criar um erro e ainda receber os parametros extras para 
	     * serem exibidos como detalhes na view
	     *
	     * @param message a mensagem de erro
	     * @param parameters os parametros do erro
	     */
	    public InternalServiceError(String message, Object... parameters) {
	        super(message);
	        this.parameters = parameters;
	    }

	    /**
	     * Construtor para criar um erro e ainda receber os parametros extras para 
	     * serem exibidos como detalhes na view
	     *
	     * @param message a mensagem de erro
	     * @param throwable o detalhamento do erro
	     * @param parameters os parametros do erro
	     */
	    public InternalServiceError(String message, Throwable throwable, Object... parameters) {
	        super(message, throwable);
	        this.parameters = parameters;
	    }
}
	

