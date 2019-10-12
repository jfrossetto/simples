package br.jfr.simples.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	public static ParameterizedType pegaParameterizedType(Class classe) {
		if( classe == null ) {
			return null ;
		} else if( classe.getGenericSuperclass() instanceof ParameterizedType ) {
			return (ParameterizedType) classe.getGenericSuperclass()  ;
		} else {
			return pegaParameterizedType(classe.getSuperclass());
		}
	}
	
	// pega 1o. campo do bean , que tenha o tipo da classe  
	public static Field pegaCampoClasse(Class bean , Class classe ) {
		for( Field f : bean.getDeclaredFields() ) {
			//System.out.println(" campo: " + f.getType().getName() );
			if( f.getType().getName().equals(classe.getName()) ) {
				return f;
			}
		}
		return null ;	
	}
	
	// faz get do nomeCampo no objeto - campo pode ser composto (camp1.camp2.camp3...)  
	public static Object pegaObjetoCampo2(Object objeto , String nomeCampo ) {
		System.out.println(" str: " + nomeCampo);
		String[] campos = nomeCampo.split("\\.",2);
		System.out.println(" [0]:" + campos[0]);
		Field f = pegaCampo(objeto,campos[0]);
		if( f == null ) {
			return null;
		}
		String objGet = Utils.nomeMetodoGetSet("get",f.getName()); 
		f.setAccessible(true);
		Object obj;
		try {
			Method m = objeto.getClass().getDeclaredMethod(objGet);
			obj =  m.invoke( objeto );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar obj principal ");
		}
		if( campos.length > 1 ) {
			System.out.println(" [1]:" + campos[1]);
			return pegaObjetoCampo2(obj,campos[1]);
		}
		return obj;
	}
		
	// faz get do nomeCampo no objeto  
	public static Object pegaObjetoCampo(Object objeto , String nomeCampo ) {
		Field f = pegaCampo(objeto,nomeCampo);
		if( f == null ) {
			return null;
		}
		String objGet = Utils.nomeMetodoGetSet("get",f.getName()); 
		f.setAccessible(true);
		try {
			Method m = objeto.getClass().getDeclaredMethod(objGet);
			return m.invoke( objeto );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar obj principal ");
		}
	}
	
	public static Field pegaCampo(Object objeto , String nomeCampo ) {
		for( Field f : objeto.getClass().getDeclaredFields() ) {
			//System.out.println(" campo: " + f.getType().getName() );
			if( f.getName().equals(nomeCampo) ) {
				return f;
			}
		}
		return null ;	
	}
	
	public static String nomeMetodoGetSet(String inicial , String campo) {
		return inicial + campo.substring(0,1).toUpperCase() + campo.substring(1);
	}
	 
	
}
