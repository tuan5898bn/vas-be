package com.vaccineadminsystem.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class VaccineDto {

	private String vaccineID;
	
	@NotNull
	private boolean active;
	
	@NotNull
	@NotBlank
	@Size(max = 50)
	private String name;
	
	@Size(max = 200)
	private String usage;
	
	@Size(max = 200)
	private String indication;
	
	@Size(max = 200)
	private String contraindication;
	
	@Max(value = 15)
	private int numberOfInjection;
	
	private Date nextTimeStart;
	private Date nextTimeEnd;
	
	@Size(max = 50)
	private String origin;
	
	@NotNull
	@NotBlank
	private String vaccineTypeId;
	
	private String vaccineTypeName;

	public VaccineDto() {
	}

	public VaccineDto(String vaccineID, boolean active, String name, String usage, String indication, String contraindication, int numberOfInjection, Date nextTimeStart, Date nextTimeEnd, String origin, String vaccineTypeId) {
		this.vaccineID = vaccineID;
		this.active = active;
		this.name = name;
		this.usage = usage;
		this.indication = indication;
		this.contraindication = contraindication;
		this.numberOfInjection = numberOfInjection;
		this.nextTimeStart = nextTimeStart;
		this.nextTimeEnd = nextTimeEnd;
		this.origin = origin;
		this.vaccineTypeId = vaccineTypeId;
	}

	public String getVaccineID() {
		return vaccineID;
	}

	public void setVaccineID(String vaccineID) {
		this.vaccineID = vaccineID;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getContraindication() {
		return contraindication;
	}

	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}

	public int getNumberOfInjection() {
		return numberOfInjection;
	}

	public void setNumberOfInjection(int numberOfInjection) {
		this.numberOfInjection = numberOfInjection;
	}

	public Date getNextTimeStart() {
		return nextTimeStart;
	}

	public void setNextTimeStart(Date nextTimeStart) {
		this.nextTimeStart = nextTimeStart;
	}

	public Date getNextTimeEnd() {
		return nextTimeEnd;
	}

	public void setNextTimeEnd(Date nextTimeEnd) {
		this.nextTimeEnd = nextTimeEnd;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getVaccineTypeId() {
		return vaccineTypeId;
	}

	public void setVaccineTypeId(String vaccineTypeId) {
		this.vaccineTypeId = vaccineTypeId;
	}

	public String getVaccineTypeName() {
		return vaccineTypeName;
	}

	public void setVaccineTypeName(String vaccineTypeName) {
		this.vaccineTypeName = vaccineTypeName;
	}

	@Override
	public String toString() {
		return "VaccineDTO [vaccineID=" + vaccineID + ", active=" + active + ", name=" + name + ", usage=" + usage
				+ ", indication=" + indication + ", contraindication=" + contraindication + ", numberOfInjection="
				+ numberOfInjection + ", nextTimeStart=" + nextTimeStart + ", nextTimeEnd=" + nextTimeEnd + ", origin="
				+ origin + ", vaccineTypeId=" + vaccineTypeId + ", vaccineTypeName=" + vaccineTypeName + "]";
	}

}
