package com.vaccineadminsystem.service;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.entity.Role;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.ExistEmployeeException;
import com.vaccineadminsystem.exception.InvalidAgeException;
import com.vaccineadminsystem.exception.SaveEmployeeException;
import com.vaccineadminsystem.repository.EmployeeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private ModelMapper modelMapper;
	
	@Autowired
	private EmployeeService employeeService;
	
	Employee employeeOne;
	Employee employeeTwo;
	EmployeeDto employeeOneDto;
	List<Employee> listOfEmployees;
	
	@Before
	public void setUp() {
		Role role = new Role(1, "ADMIN");
		employeeOne = new Employee("EMP001", new GregorianCalendar(1998, Calendar.DECEMBER, 12).getTime(), 
				"dungbt@gmail.com", "Tuan Dung", "dungbt", 
				"$2y$12$74gA.EtZQ6rOWRcr8a8EfuTqnREggUtxFDpnxXGRauCiwNijUq09e", "0984354135", role);
		employeeTwo = new Employee("EMP002", new GregorianCalendar(1998, Calendar.NOVEMBER, 28).getTime(), 
				"luannt@gmail.com", "Thanh Luan", "luannt", 
				"$2y$12$74gA.EtZQ6rOWRcr8a8EfuTqnREggUtxFDpnxXGRauCiwNijUq09e", "0784354985", role);
		employeeOneDto = new EmployeeDto("EMP001", new GregorianCalendar(1998, Calendar.DECEMBER, 12).getTime(), 
				"dungbt@gmail.com", "Tuan Dung", "dungbt", 
				"$2y$12$74gA.EtZQ6rOWRcr8a8EfuTqnREggUtxFDpnxXGRauCiwNijUq09e", "0984354135");
		listOfEmployees = Arrays.asList(employeeOne, employeeTwo); 
	}
	
	@Test
	public void testGetAll() {
		when(employeeRepository.findAll()).thenReturn(listOfEmployees);
		Assert.assertEquals(2, employeeService.getAll().size());
	}
	
	@Test
	public void testGetByUsernameSuccess() {
        when(employeeRepository.findByUsername(employeeOne.getUsername())).thenReturn(Optional.of(employeeOne));
        Assert.assertNotNull(employeeService.getByUsername("dungbt"));
	}
	
	@Test
	public void testGetByUsernameFail() {
        when(employeeRepository.findByUsername(employeeOne.getUsername())).thenReturn(Optional.empty());
        Assert.assertNull(employeeService.getByUsername("maihn"));
	}
	
	@Test
	public void testSaveSuccess() throws InvalidAgeException, ExistEmployeeException, SaveEmployeeException {
		when(employeeRepository.findById(employeeOneDto.getEmployeeId())).thenReturn(Optional.empty());
        when(employeeRepository.save(employeeOne)).thenReturn(employeeOne);
        when(modelMapper.map(employeeOneDto, Employee.class)).thenReturn(employeeOne);
        when(modelMapper.map(employeeOne, EmployeeDto.class)).thenReturn(employeeOneDto);
        Assert.assertNotNull(employeeService.save(employeeOneDto));
	}
}
