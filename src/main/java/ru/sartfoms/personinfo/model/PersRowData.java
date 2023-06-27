package ru.sartfoms.personinfo.model;


import java.time.LocalDate;

import org.thymeleaf.util.StringUtils;

public class PersRowData {
	
	private String oip;
	
	private String enp;
	
	private LocalDate pcyEffDt;
	
	private String pcyType;
	
	private String pcyStatus;
	
	private String dsourceType;
	
	private String gender;
	
	private String lastName;
	
	private String firstName;
	
	private String patronymic;
	
	private LocalDate birthDay;
	
	private String blankNum;
	
	private String dudlNum;
	
	private String dudlSer;
	
	private LocalDate dudlDateB;
	
	private LocalDate dudlDateE;
	
	private String dudlStatus;
	
	private String snils;
	
	private LocalDate pcyExpDt;

	public String getOip() {
		return oip;
	}

	public void setOip(String oip) {
		this.oip = oip;
	}

	public String getEnp() {
		return enp;
	}

	public void setEnp(String enp) {
		this.enp = enp;
	}

	public String getPcyType() {
		return pcyType;
	}

	public void setPcyType(String pcyType) {
		this.pcyType = pcyType;
	}

	public String getPcyStatus() {
		return pcyStatus;
	}

	public void setPcyStatus(String pcyStatus) {
		this.pcyStatus = pcyStatus;
	}

	public String getDsourceType() {
		return dsourceType;
	}

	public void setDsourceType(String dsourceType) {
		this.dsourceType = dsourceType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return lastName != null ? StringUtils.capitalize(lastName.toLowerCase()) : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName != null ? StringUtils.capitalize(firstName.toLowerCase()) : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPatronymic() {
		return patronymic != null ? StringUtils.capitalize(patronymic.toLowerCase()) : patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getBlankNum() {
		return blankNum;
	}

	public void setBlankNum(String blankNum) {
		this.blankNum = blankNum;
	}

	public String getDudlNum() {
		return dudlNum;
	}

	public void setDudlNum(String dudlNum) {
		this.dudlNum = dudlNum;
	}

	public String getDudlSer() {
		return dudlSer;
	}

	public void setDudlSer(String dudlSer) {
		this.dudlSer = dudlSer;
	}

	public String getDudlStatus() {
		return dudlStatus;
	}

	public void setDudlStatus(String dudlStatus) {
		this.dudlStatus = dudlStatus;
	}

	public String getSnils() {
		return snils;
	}

	public void setSnils(String snils) {
		this.snils = snils;
	}

	public LocalDate getPcyEffDt() {
		return pcyEffDt;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public LocalDate getDudlDateB() {
		return dudlDateB;
	}

	public LocalDate getDudlDateE() {
		return dudlDateE;
	}

	public LocalDate getPcyExpDt() {
		return pcyExpDt;
	}

	public void setPcyEffDt(LocalDate pcyEffDt) {
		this.pcyEffDt = pcyEffDt;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public void setDudlDateB(LocalDate dudlDateB) {
		this.dudlDateB = dudlDateB;
	}

	public void setDudlDateE(LocalDate dudlDateE) {
		this.dudlDateE = dudlDateE;
	}

	public void setPcyExpDt(LocalDate pcyExpDt) {
		this.pcyExpDt = pcyExpDt;
	}

}
