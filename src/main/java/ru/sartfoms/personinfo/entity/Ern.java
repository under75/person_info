package ru.sartfoms.personinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "mpi_ern", schema = "ASY23")
@IdClass(CompositeKey.class)
public class Ern {
	@Id
	@Column(name = "rid")
	private Long rid;

	@Id
	@Column(name = "nr")
	private Integer nr;

	@Column(name = "ern")
	private String ern;

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE) 
	@JoinColumn(name = "dsource", referencedColumnName = "cod", insertable=false, updatable=false)
	private Okato dsource;
	
	@Column(name = "dsource")
	private String dsourceStr;

	@Column(name = "dsourcetype")
	private String dsourceType;

	@Column(name = "ernstatus")
	private String ernstatus;

	public Long getRid() {
		return rid;
	}

	public Integer getNr() {
		return nr;
	}

	public String getErn() {
		return ern;
	}

	public Okato getDsource() {
		return dsource;
	}

	public String getDsourceStr() {
		return dsourceStr;
	}

	public String getDsourceType() {
		return dsourceType;
	}

	public String getErnstatus() {
		return ernstatus;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public void setErn(String ern) {
		this.ern = ern;
	}

	public void setDsource(Okato dsource) {
		this.dsource = dsource;
	}

	public void setDsourceStr(String dsourceStr) {
		this.dsourceStr = dsourceStr;
	}

	public void setDsourceType(String dsourceType) {
		this.dsourceType = dsourceType;
	}

	public void setErnstatus(String ernstatus) {
		this.ernstatus = ernstatus;
	}

}
