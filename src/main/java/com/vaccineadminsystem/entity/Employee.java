package com.vaccineadminsystem.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "employee_id", length = 36)
    private String employeeId;

    @Column(length = 255)
    private String address;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(length = 100)
    private String email;

    @Column(name="employee_name", length = 100)
    private String employeeName;

    @Column(length = 10)
    private Integer gender;

    @Column(length = 255)
    private String image;

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String position;

    @Column(length = 255)
    private String username;

    @Column(length = 255, name = "working_place")
    private String workingPlace;

    @ManyToOne
    private Role role;

    public Employee() {
    }

    public Employee(String employeeId, Date dateOfBirth, String email,
                    String employeeName,String username, String password, String phone,
                    Role role) {
        this.employeeId = employeeId;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.employeeName = employeeName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
