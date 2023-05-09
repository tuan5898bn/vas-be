package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.exception.ExistEmployeeException;
import com.vaccineadminsystem.exception.InvalidAgeException;

import com.vaccineadminsystem.exception.SaveEmployeeException;
import java.util.List;

public interface EmployeeService {

    Employee getByUsername(String username);

    List<EmployeeDto> getAll();

    EmployeeDto save(EmployeeDto employeeDto)
        throws InvalidAgeException, ExistEmployeeException,
        SaveEmployeeException;

    EmployeeDto update(String empId,EmployeeDto employeeDto) throws InvalidAgeException;

    void deleteById(String employeeId);

    EmployeeDto getEmployeeById(String employeeId);

    boolean isExistEmployee(String employeeId);

    boolean deleteByIds(List<String> ids) throws ConstraintException;
}
