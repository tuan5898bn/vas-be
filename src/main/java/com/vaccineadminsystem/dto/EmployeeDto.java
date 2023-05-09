package com.vaccineadminsystem.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.vaccineadminsystem.entity.Role;

import java.util.Date;

public class EmployeeDto {


    private String employeeId;

    private String address;

    @NotNull(message = "{dateOfBirth.notnull}")
    private Date dateOfBirth;

    @NotBlank(message = "{email.notblank}")
    @Email(message = "{email.format}")
    private String email;

    @NotNull(message = "{empName.notnull}")
    private String employeeName;

    private Integer gender;

    private String image;

    @NotBlank(message = "{phone.notblank}")
    private String phone;

    private String position;

    private String username;

    private String workingPlace;

    @NotEmpty(message = "{password.notempty}")
    private String password;

    public EmployeeDto() {
    }
    
    public EmployeeDto(String employeeId, Date dateOfBirth, String email,
            String employeeName,String username, String password, String phone) {
    	this.employeeId = employeeId;
    	this.dateOfBirth = dateOfBirth;
    	this.email = email;
    	this.employeeName = employeeName;
    	this.username = username;
    	this.password = password;
    	this.phone = phone;
    }

	public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
