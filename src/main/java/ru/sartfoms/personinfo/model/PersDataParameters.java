package ru.sartfoms.personinfo.model;

import java.util.Collection;

import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import ru.sartfoms.personinfo.service.PersonDataService.Show;

public class PersDataParameters {
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
	@Size(max=40)
	private String lastName;
	@Size(max=40)
	private String firstName;
	@Size(max=40)
	private String patronymic;
	private Boolean historical = false;
	private String dt;
	private String dtFrom;
	private String dtTo;
	private String show = StringUtils.arrayToCommaDelimitedString(Show.values());
	private String dateFrom;
	private String dateTo;
	private Collection<Long> selectedRows;
	
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
	public Integer getDudlType() {
		return dudlType;
	}
	public String getDudlSer() {
		return dudlSer;
	}
	public String getDudlNum() {
		return dudlNum;
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
	public String getSnils() {
		return snils;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public void setSnils(String snils) {
		this.snils = snils;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public Boolean getHistorical() {
		return historical;
	}
	public String getDt() {
		return dt;
	}
	public String getDtFrom() {
		return dtFrom;
	}
	public String getDtTo() {
		return dtTo;
	}
	public String getShow() {
		return show;
	}
	public void setHistorical(Boolean historical) {
		this.historical = historical;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public void setDtFrom(String dtFrom) {
		this.dtFrom = dtFrom;
	}
	public void setDtTo(String dtTo) {
		this.dtTo = dtTo;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public Collection<Long> getSelectedRows() {
		return selectedRows;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public void setSelectedRows(Collection<Long> selectedRows) {
		this.selectedRows = selectedRows;
	}
	public String getDudlEffDate() {
		return dudlEffDate;
	}
	public void setDudlEffDate(String dudlEffDate) {
		this.dudlEffDate = dudlEffDate;
	}
}
