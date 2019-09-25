package br.jfr.simples.util;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.Validator;

public class Validador {
	
	public boolean ValidaCPF(String doc) {
		CPFValidator cpfValidator = new CPFValidator() ;
		try {
			cpfValidator.assertValid(doc);
			return true ;
        } catch (Exception ex) {
            return false ;
        }
	}
	
	public boolean ValidaCNPJ(String doc) {
		CNPJValidator cnpjValidator = new CNPJValidator() ;
		try {
			cnpjValidator.assertValid(doc);
			return true ;
        } catch (Exception ex) {
            return false ;
        }
	}

}
