package br.jfr.simples.model.db;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.jfr.simples.converter.BooleanToStringConverter;

@MappedSuperclass
@EntityListeners(EntidadeListener.class)
public abstract class Entidade implements IEntidade , Serializable {
	
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criadoem")
    private Calendar criadoEm;
	
	@ManyToOne
	@JoinColumn(name = "criadopor")
    private Usuario criadoPor;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alteradoem")
    private Calendar AlteradoEm;
    
	@ManyToOne
	@JoinColumn(name = "alteradopor")
    private Usuario alteradoPor;

    @Column(name="regativo",length=1)
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean regAtivo;
	
	public Calendar getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Calendar criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Usuario getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Calendar getAlteradoEm() {
		return AlteradoEm;
	}

	public void setAlteradoEm(Calendar alteradoEm) {
		AlteradoEm = alteradoEm;
	}

	public Usuario getAlteradoPor() {
		return alteradoPor;
	}

	public void setAlteradoPor(Usuario alteradoPor) {
		this.alteradoPor = alteradoPor;
	}

	public Boolean getRegAtivo() {
		return regAtivo;
	}

	public void setRegAtivo(Boolean regAtivo) {
		this.regAtivo = regAtivo;
	}

	@Override
    public boolean equals(Object other) {
        return (other instanceof IEntidade) && (getId() != null)
            ? getId().equals(((IEntidade) other).getId())
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (getId() != null)
            ? (this.getClass().hashCode() + getId().hashCode())
            : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("PersistentEntity[%d, %s]", getId(), this.getClass().getName());
    }
	
}
