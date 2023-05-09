package com.vaccineadminsystem.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

@Entity
@Table(name = "VACCINE")
public class Vaccine {
	@Id
	@Column(name = "vaccine_id", length = 10)
	private String vaccineID;

	@Column(name = "active", length = 50, nullable = false)
	private boolean active;

	@Column(name = "vaccine_name", length = 50, nullable = false)
	private String name;

	@Column(name = "usage", length = 200)
	private String usage;

	@Column(name = "indication", length = 200)
	private String indication;

	@Column(name = "contraindication", length = 200)
	private String contraindication;

	@Max(value = 15)
	@Column(name = "number_of_injection")
	private int numberOfInjection;

	@Column(name = "time_begin_next_injection")
	@Temporal(TemporalType.DATE)
	private Date nextTimeStart;

	@Column(name = "time_end_next_injection")
	@Temporal(TemporalType.DATE)
	private Date nextTimeEnd;

	@Column(name = "origin", length = 50)
	private String origin;

	@ManyToOne
	@JoinColumn(name = "vaccineType_id")
	private VaccineType vaccineType;

	public Vaccine() {
	}

	public Vaccine(String vaccineID, boolean active, String name, String usage, String indication, String contraindication, int numberOfInjection, Date nextTimeStart, Date nextTimeEnd, String origin, VaccineType vaccineType) {
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
		this.vaccineType = vaccineType;
	}

	public Vaccine(String vaccineID, boolean active, String name,
				   String origin) {
		this.vaccineID = vaccineID;
		this.active = active;
		this.name = name;
		this.origin = origin;
	}
	public Vaccine(String vaccineID, boolean active, String name, VaccineType vaccineType) {
		this.vaccineID = vaccineID;
		this.active = active;
		this.name = name;
		this.vaccineType = vaccineType;
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

	public VaccineType getVaccineType() {
		return vaccineType;
	}

	public void setVaccineType(VaccineType vaccineType) {
		this.vaccineType = vaccineType;
	}

}
