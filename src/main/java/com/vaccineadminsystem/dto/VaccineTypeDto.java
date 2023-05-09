package com.vaccineadminsystem.dto;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VaccineTypeDto {
    @NotBlank
    @Size(max = 36)
    private String id;

    @Column (name= "DESCRIPTION")
    @Size(max=200)
    private String description;

    @NotBlank
    @Size(max=50)
    private String vaccineTypeName;
    @NotNull
    private boolean status;

    private String image;

    public VaccineTypeDto() {
    }

    public VaccineTypeDto(@NotBlank @Size(max = 36) String id, @NotBlank @Size(max = 50) String vaccineTypeName, @NotNull boolean status) {
        this.id = id;
        this.vaccineTypeName = vaccineTypeName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
