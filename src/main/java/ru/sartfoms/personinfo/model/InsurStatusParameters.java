package ru.sartfoms.personinfo.model;

import javax.validation.constraints.Size;

public class InsurStatusParameters {
	@Size(max=12)
	private String oip;
	private String policyType;
	@Size(max=10)
	private String policySer;
	@Size(max=16)
	private String policyNum;
	private Integer dudlType;
	@Size(max=12)
	private String dudlSer;
	@Size(max=20)
	private String dudlNum;
	private String dudlEffDate;
	@Size(max=14)
	private String snils;
	private String birthDay;
	private String dt;
	private String dateFrom;
	private String dateTo;
	public String getOip() {
		return oip;
	}
	public String getPolicyType() {
		return policyType;
	}
	public String getPolicySer() {
		return policySer;
	}
	public String getPolicyNum() {
		return policyNum;
	}
	public Integer getDudlType() {
		return dudlType;
	}
	public String getDudlSer() {
		return dudlSer;
	}
	public String getDudlNum() {
		return dudlNum;
	}
	public String getDudlEffDate() {
		return dudlEffDate;
	}
	public String getSnils() {
		return snils;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public String getDt() {
		return dt;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setOip(String oip) {
		this.oip = oip.trim();
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public void setPolicySer(String policySer) {
		this.policySer = policySer;
	}
	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}
	public void setDudlType(Integer dudlType) {
		this.dudlType = dudlType;
	}
	public void setDudlSer(String dudlSer) {
		this.dudlSer = dudlSer;
	}
	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum;
	}
	public void setDudlEffDate(String dudlEffDate) {
		this.dudlEffDate = dudlEffDate;
	}
	public void setSnils(String snils) {
		this.snils = snils;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
}
