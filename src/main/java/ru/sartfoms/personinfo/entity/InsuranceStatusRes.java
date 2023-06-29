package ru.sartfoms.personinfo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "mpi_insurance_status_resp", schema = "ASY23")
public class InsuranceStatusRes {
	@Id
	@Column(name = "rid")
	private Long rid;
	
	@Column(name = "oip")
	private String oip;
	
	@Column(name = "fio")
	private String fio;
	
	@Column(name = "dr")
	private LocalDate birthDay;
	
	@Column(name = "sex")
	private Integer gender;
	
	@Column(name = "policytype")
	private String pcyType;
	
	@Column(name = "policyser")
	private String pcySer;

	@Column(name = "policynumenp")
	private String pcyNum;
	
	@Column(name = "policystatus")
	private String pcyStatus;
	
	@Column(name = "smo")
	private Integer smoCode;
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "ter", referencedColumnName = "cod", insertable = false, updatable = false)
	private Okato terr;

	@Column(name = "ter")
	private String okatoCode;
	
	@Column(name = "policyvalidfrom")
	private LocalDate policyValidFrom;
	
	@Column(name = "policyvalidto")
	private LocalDate policyValidTo;

	public Long getRid() {
		return rid;
	}

	public String getOip() {
		return oip;
	}

	public String getFio() {
		return fio;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public Integer getGender() {
		return gender;
	}

	public String getPcyType() {
		return pcyType;
	}

	public String getPcySer() {
		return pcySer;
	}

	public String getPcyNum() {
		return pcyNum;
	}

	public String getPcyStatus() {
		return pcyStatus;
	}

	public Integer getSmoCode() {
		return smoCode;
	}

	public String getOkatoCode() {
		return okatoCode;
	}

	public LocalDate getPolicyValidFrom() {
		return policyValidFrom;
	}

	public LocalDate getPolicyValidTo() {
		return policyValidTo;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setPcyType(String pcyType) {
		this.pcyType = pcyType;
	}

	public void setPcySer(String pcySer) {
		this.pcySer = pcySer;
	}

	public void setPcyNum(String pcyNum) {
		this.pcyNum = pcyNum;
	}

	public void setPcyStatus(String pcyStatus) {
		this.pcyStatus = pcyStatus;
	}

	public void setSmoCode(Integer smoCode) {
		this.smoCode = smoCode;
	}

	public void setOkatoCode(String okatoCode) {
		this.okatoCode = okatoCode;
	}

	public void setPolicyValidFrom(LocalDate policyValidFrom) {
		this.policyValidFrom = policyValidFrom;
	}

	public void setPolicyValidTo(LocalDate policyValidTo) {
		this.policyValidTo = policyValidTo;
	}

	public Okato getTerr() {
		return terr;
	}

	public void setTerr(Okato terr) {
		this.terr = terr;
	}

}
