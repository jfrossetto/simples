package br.jfr.simples.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(PersistentEntityListener.class)
public abstract class PersistentEntity implements IPersistentEntity , Serializable {
	
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Calendar creationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update_date")
    private Calendar lastUpdateDate;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Override
    public boolean equals(Object other) {
        return (other instanceof IPersistentEntity) && (getId() != null)
            ? getId().equals(((IPersistentEntity) other).getId())
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
