package com.vaccineadminsystem.dto;

public class ReportVaccineTypeChart {

    private long numberOfVaccine;
    private String vaccineTypeName;

    public ReportVaccineTypeChart(long numberOfVaccine, String vaccineTypeName) {
        this.numberOfVaccine = numberOfVaccine;
        this.vaccineTypeName = vaccineTypeName;
    }

    public ReportVaccineTypeChart() {
    }

    public long getNumberOfVaccine() {
        return numberOfVaccine;
    }

    public void setNumberOfVaccine(long numberOfVaccine) {
        this.numberOfVaccine = numberOfVaccine;
    }

    public String getVaccineTypeName() {
        return vaccineTypeName;
    }

    public void setVaccineTypeName(String vaccineTypeName) {
        this.vaccineTypeName = vaccineTypeName;
    }
}
