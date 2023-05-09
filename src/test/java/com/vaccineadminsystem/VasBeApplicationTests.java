package com.vaccineadminsystem;

import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.entity.Role;
import com.vaccineadminsystem.repository.EmployeeRepository;
import com.vaccineadminsystem.service.UserDetailsServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VasBeApplicationTests {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmployeeRepository employeeRepository;
    Employee employee1 = null;
    Employee employee2 = null;


    @Before
    public void setUp() throws Exception {
        Role adminRole = new Role(1, "ROLE_ADMIN");
        Role userRole = new Role(2, "ROLE_USER");
        employee1 = new Employee();
        employee1.setPassword("$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG");
        employee1.setRole(adminRole);
        employee1.setEmployeeId("000022562");
        employee1.setUsername("anhnd52123");
        employee1.setEmployeeName("duc anh");

        employee2 = new Employee();
        employee2.setPassword("$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG"); // pass: 123456789
        employee2.setRole(userRole);
        employee2.setEmployeeId("032115512");
        employee2.setUsername("anhnd12312");
        employee2.setEmployeeName("namLX");
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

    }

    @Test
    public void test() {
        when(employeeRepository.findByUsername("anhnd12312")).thenReturn(Optional.of(employee2));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("anhnd12312", "123456789")
        );
        String a = "a";
    }


}
