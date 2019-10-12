package br.jfr.simples.model.bean;

public class ModalBusca {
	
	private String titulo ;
	private String urlLista ;
	private String campo ;
	
	public ModalBusca() {
		super();
	}

	public String getTitulo() {
		return titulo;
	}

	public ModalBusca setTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	public String getUrlLista() {
		return urlLista;
	}

	public ModalBusca setUrlLista(String urlLista) {
		this.urlLista = urlLista;
		return this;
	}

	public String getCampo() {
		return campo;
	}

	public ModalBusca setCampo(String campo) {
		this.campo = campo;
		return this;
	}
	
	

}
