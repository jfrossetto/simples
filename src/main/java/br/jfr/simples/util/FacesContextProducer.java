package br.jfr.simples.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;

public class FacesContextProducer {
	
//    @Produces
//    @RequestScoped
//    RequestContext produceRequestContext() {
//        return RequestContext.getCurrentInstance();
//    }

    /**
     * Produz um contexto valido do {@link FacesContext}
     *
     * @return um {@link FacesContext} valido
     */
    @Produces
    @RequestScoped
    FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    PrimeFaces producePrimeContext() {
        return PrimeFaces.current();
    }

}
