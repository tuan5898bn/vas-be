package com.vaccineadminsystem.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.vaccineadminsystem.dto.VaccineTypeDto;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.entity.Role;
import com.vaccineadminsystem.entity.VaccineType;
import com.vaccineadminsystem.exception.ExistVaccineTypeIdException;
import com.vaccineadminsystem.exception.SaveInActiveVaccineTypeException;
import com.vaccineadminsystem.repository.EmployeeRepository;
import com.vaccineadminsystem.repository.VaccineTypeRepository;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    VaccineTypeRepository vaccineTypeRepository;

    @Test
    public void testLoadByUsernameExistEmployee() {
        String username = "uname1";
        Role roleAdmin = new Role(1, "ROLE_ADMIN");
        Employee employee1 =
            new Employee("emp1", new Date(), "ducanh@gmail.com", "duc anh",
                "uname1",
                "$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG",
                "0358705659", roleAdmin);
        when(employeeRepository.findByUsername(username)).thenReturn(
            Optional.of(employee1));
        assertNotNull(userDetailsServiceImp.loadUserByUsername(username));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadByUsernameNotFoundEmployee() {
        Role roleAdmin = new Role(1, "ROLE_ADMIN");
        String username = "ducanh123";
        Employee employee1 =
            new Employee("emp1", new Date(), "ducanh@gmail.com", "duc anh",
                "ducanh12345",
                "$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG",
                "0358705659", roleAdmin);
        when(employeeRepository.findByUsername(username)).thenReturn(
            Optional.empty());
        userDetailsServiceImp.loadUserByUsername(username);
    }

    @Test(expected = NullPointerException.class)
    public void testLoadByUsernameNullUsername() {
        when(employeeRepository.findByUsername(null))
            .thenThrow(NullPointerException.class);
        userDetailsServiceImp.loadUserByUsername(null);
    }




}
