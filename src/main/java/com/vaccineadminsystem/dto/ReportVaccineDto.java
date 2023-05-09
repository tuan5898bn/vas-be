package com.vaccineadminsystem.dto;

public class ReportVaccineDto {
	
	private String vaccineTypeId;
	private String vaccineTypeName;
	private boolean status;
	private long quantity;
	private String origin;
	
	public ReportVaccineDto(String vaccineTypeId, String vaccineTypeName, boolean status, long quantity, String origin) {
		this.vaccineTypeId = vaccineTypeId;
		this.vaccineTypeName = vaccineTypeName;
		this.status = status;
		this.quantity = quantity;
		this.origin = origin;
	}

	public ReportVaccineDto() {
		
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
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
