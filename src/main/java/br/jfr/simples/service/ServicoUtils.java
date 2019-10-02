package br.jfr.simples.service;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.slf4j.Logger;

public abstract class ServicoUtils implements IServicoUtils {
	
	public ParameterizedType pegaParameterizedType(Class classe) {
		if( classe == null ) {
			return null ;
		} else if( classe.getGenericSuperclass() instanceof ParameterizedType ) {
			return (ParameterizedType) classe.getGenericSuperclass()  ;
		} else {
			return pegaParameterizedType(classe.getSuperclass());
		}
	}
	
	public String retornaMD5(String str) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(),0,str.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String formataDataDma(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}


}
