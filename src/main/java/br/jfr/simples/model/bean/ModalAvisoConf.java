package br.jfr.simples.model.bean;

public class ModalAvisoConf {
	
	private String titulo ;
	private String msgCorpo ;
	private boolean btn1 ;
	private boolean btn2 ;
	private boolean btn3 ; 
	private String btn1Label ;
	private String btn2Label ; 
	private String btn3Label ;
	private String btn1Classe ;
	private String btn2Classe ; 
	private String btn3Classe;
	
	public ModalAvisoConf() {
		super();
		btn1 = true ; 
		btn2 = false ;
		btn3 = false ; 
		btn1Label = "Ok";
		btn1Classe = "btn-primary";
	}
	
	public ModalAvisoConf setSimNao() {
		btn1 = true ; 
		btn2 = true ;
		btn3 = false ; 
		btn1Label = "Não";
		btn2Label = "Sim";
		btn1Classe = "btn-success";
		btn2Classe = "btn-danger";
		return this;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public ModalAvisoConf setTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}
	public String getMsgCorpo() {
		return msgCorpo;
	}
	public ModalAvisoConf setMsgCorpo(String msgCorpo) {
		this.msgCorpo = msgCorpo;
		return this;
	}

	public boolean isBtn1() {
		return btn1;
	}

	public ModalAvisoConf setBtn1(boolean btn1) {
		this.btn1 = btn1;
		return this;
	}

	public boolean isBtn2() {
		return btn2;
	}

	public ModalAvisoConf setBtn2(boolean btn2) {
		this.btn2 = btn2;
		return this;
	}

	public boolean isBtn3() {
		return btn3;
	}

	public ModalAvisoConf setBtn3(boolean btn3) {
		this.btn3 = btn3;
		return this;
	}

	public String getBtn1Label() {
		return btn1Label;
	}

	public ModalAvisoConf setBtn1Label(String btn1Label) {
		this.btn1Label = btn1Label;
		return this;
	}

	public String getBtn2Label() {
		return btn2Label;
	}

	public ModalAvisoConf setBtn2Label(String btn2Label) {
		this.btn2Label = btn2Label;
		return this;
	}

	public String getBtn3Label() {
		return btn3Label;
	}

	public ModalAvisoConf setBtn3Label(String btn3Label) {
		this.btn3Label = btn3Label;
		return this;
	}

	public String getBtn1Classe() {
		return btn1Classe;
	}

	public ModalAvisoConf  setBtn1Classe(String btn1Classe) {
		this.btn1Classe = btn1Classe;
		return this;
	}

	public String getBtn2Classe() {
		return btn2Classe;
	}

	public ModalAvisoConf  setBtn2Classe(String btn2Classe) {
		this.btn2Classe = btn2Classe;
		return this;
	}

	public String getBtn3Classe() {
		return btn3Classe;
	}

	public ModalAvisoConf  setBtn3Classe(String btn3Classe) {
		this.btn3Classe = btn3Classe;
		return this;
	}

}
