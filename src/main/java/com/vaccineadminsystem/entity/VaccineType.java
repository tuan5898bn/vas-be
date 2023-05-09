package com.vaccineadminsystem.entity;




import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "VACCINE_TYPE")
public class VaccineType {

    @Id
    @Column(name = "VACCINE_TYPE_ID")
    @NotBlank
    @Size(max = 36)
    private String id;
    @Column (name= "DESCRIPTION")
    @Size(max=200)
    private String description;

    @Column (name = "VACCINE_TYPE_NAME")
    @NotBlank
    @Size(max=50)
    private String vaccineTypeName;

    @Column (name = "STATUS",nullable = false)
    private Boolean status;
    @Column (name = "IMAGE")
    private String image;

    @OneToMany(mappedBy = "vaccineType")
    private List<Vaccine> vaccines;

    public VaccineType() {
    }

    public VaccineType(@NotBlank @Size(max = 36) String id, @NotBlank @Size(max = 50) String vaccineTypeName, Boolean status) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Vaccine> getVaccines() {
        return vaccines;
    }

    public void setVaccines(List<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

}
