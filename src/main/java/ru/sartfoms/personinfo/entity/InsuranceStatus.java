package ru.sartfoms.personinfo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mpi_insurance_status", schema = "ASY23")
public class InsuranceStatus {
	@Id
	@Column(name = "rid")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
	@SequenceGenerator(name = "id_sequence", sequenceName = "ASY23.MPI_SEQ", allocationSize = 1)
	private Long rid;
	
	@Column(name = "oip")
	private String oip;

	@Column(name = "polis_type")
	private String pcyType;

	@Column(name = "pcyser")
	private String pcySer;

	@Column(name = "polis")
	private String pcy;
	
	@Column(name = "snils")
	private String snils;

	@Column(name = "dr")
	private LocalDate birthDay;
	
	@Column(name = "dudlser")
	private String dudlSer;
	
	@Column(name = "dudlnum")
	private String dudlNum;

	@Column(name = "dudltype")
	private Integer dudlType;
	
	@Column(name = "dudldateb")
	private LocalDate dudlEffDate;
	
	@Column(name = "dt_ins")
	private LocalDateTime dtIns;
	
	@Column(name = "usr")
	private String user;
	
	@Column(name = "err")
	private Boolean hasError;
	
	@Column(name = "dt")
	private LocalDate dt;

	public Long getRid() {
		return rid;
	}

	public String getOip() {
		return oip;
	}

	public String getPcyType() {
		return pcyType;
	}

	public String getPcySer() {
		return pcySer;
	}

	public String getPcy() {
		return pcy;
	}

	public String getSnils() {
		return snils;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public String getDudlSer() {
		return dudlSer;
	}

	public String getDudlNum() {
		return dudlNum;
	}

	public Integer getDudlType() {
		return dudlType;
	}

	public LocalDate getDudlEffDate() {
		return dudlEffDate;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

	public void setPcyType(String pcyType) {
		this.pcyType = pcyType;
	}

	public void setPcySer(String pcySer) {
		this.pcySer = pcySer;
	}

	public void setPcy(String pcy) {
		this.pcy = pcy;
	}

	public void setSnils(String snils) {
		this.snils = snils;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public void setDudlSer(String dudlSer) {
		if (dudlSer.matches("^\\d{4}$")) {
			dudlSer = dudlSer.substring(0, 2) + " " + dudlSer.substring(2);
		}
		this.dudlSer = dudlSer;
	}

	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum;
	}

	public void setDudlType(Integer dudlType) {
		this.dudlType = dudlType;
	}

	public void setDudlEffDate(LocalDate dudlEffDate) {
		this.dudlEffDate = dudlEffDate;
	}

	public LocalDateTime getDtIns() {
		return dtIns;
	}

	public void setDtIns(LocalDateTime dtIns) {
		this.dtIns = dtIns;
	}

	public String getUser() {
		return user;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public LocalDate getDt() {
		return dt;
	}

	public void setDt(LocalDate dt) {
		this.dt = dt;
	}
}
