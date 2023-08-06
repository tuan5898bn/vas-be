package com.vaccineadminsystem.service.imp;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.entity.Role;
import com.vaccineadminsystem.exception.ConstraintException;
import com.vaccineadminsystem.exception.ExistEmployeeException;
import com.vaccineadminsystem.exception.InvalidAgeException;
import com.vaccineadminsystem.exception.SaveEmployeeException;
import com.vaccineadminsystem.repository.EmployeeRepository;
import com.vaccineadminsystem.service.EmployeeService;
import com.vaccineadminsystem.util.ErrorMess;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class  EmployeeServiceImp implements EmployeeService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Employee getByUsername(String username) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        return employee.orElse(null);
    }

    @Override
    @Transactional
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> list = null;
        try {
            list = employees.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }


    private EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

    /**
     * convert employee dto to employee and encode password and set role then save object to db
     *
     * @param employeeDto employee insert from client
     * @return a employee is inserted in db, null if save fail
     */
    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto)
        throws InvalidAgeException, ExistEmployeeException,
        SaveEmployeeException {
        Date now = new Date();
        if ((employeeDto.getDateOfBirth() != null) && (now.getYear() - employeeDto.getDateOfBirth().getYear()) < 18) {
            throw new InvalidAgeException(ErrorMess.INVALID_AGE);
        }
        if (isExistEmployee(employeeDto.getEmployeeId())) {
            throw new ExistEmployeeException(ErrorMess.EXIST_EMPLOYEE);
        }
        Employee employee = convertToEntity(employeeDto);
        Employee resEmployee = null;
        try {
            String passwordEncode = passwordEncoder.encode(employeeDto.getPassword());
            employee.setPassword(passwordEncode);
            employee.setRole(new Role(2, "ROLE_USER"));
            resEmployee = employeeRepository.save(employee);
        } catch (Exception e) {
            e.printStackTrace();// throw exception
            throw new SaveEmployeeException(e.getMessage());
        }
        return resEmployee == null ? null : this.convertToDto(resEmployee);
    }

    /**
     * @param empId       employee id
     * @param employeeDto data to update from client
     * @return a new is updated from transaction, null if old employee is non exist to update
     */
    @Override
    @Transactional
    public EmployeeDto update(String empId, EmployeeDto employeeDto) throws InvalidAgeException {
        Date now = new Date();
        if ((now.getYear() - employeeDto.getDateOfBirth().getYear()) < 18) {
            throw new InvalidAgeException(ErrorMess.INVALID_AGE);
        }
        Optional<Employee> optEmp = employeeRepository.findById(empId);
        if (optEmp.isPresent()) {
            Employee newE = oldToNewEmployee(optEmp.get(), employeeDto);
            return convertToDto(employeeRepository.save(newE));
        }
        return null;
    }

    private Employee oldToNewEmployee(Employee oldEmp, EmployeeDto newEmp) {
        oldEmp.setAddress(newEmp.getAddress());
        oldEmp.setEmployeeName(newEmp.getEmployeeName());
        oldEmp.setEmail(newEmp.getEmail());
        oldEmp.setGender(newEmp.getGender());
        oldEmp.setDateOfBirth(newEmp.getDateOfBirth());
        oldEmp.setPosition(newEmp.getPosition());
        oldEmp.setWorkingPlace(newEmp.getWorkingPlace());
        oldEmp.setPhone(newEmp.getPhone());
        oldEmp.setImage(newEmp.getImage());
        return oldEmp;
    }


    @Override
    public void deleteById(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeDto getEmployeeById(String employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        EmployeeDto employeeDto = null;
        if (employee.isPresent()) {
            employeeDto = convertToDto(employee.get());
        } else {
            logger.info("there is no employee for id " + employeeId);
        }
        return employeeDto;
    }

    @Override
    public boolean isExistEmployee(String employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(employeeId);
        return optionalEmployee.isPresent();
    }

    @Override
    @Transactional
    public boolean deleteByIds(List<String> ids) throws ConstraintException {
        try {
            employeeRepository.deleteEmployeesWithIds(ids);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ConstraintException(ErrorMess.EMPLOYEE_CONSTRAINT);
        }
    }

}
