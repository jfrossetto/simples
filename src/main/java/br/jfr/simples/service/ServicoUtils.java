package br.jfr.simples.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import org.slf4j.Logger;

public abstract class ServicoUtils implements IServicoUtils {
	
	@Inject
	public Logger logger;

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


}
