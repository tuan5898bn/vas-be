package com.vaccineadminsystem.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.vaccineadminsystem.entity.Employee;
import com.vaccineadminsystem.entity.Role;
import com.vaccineadminsystem.exception.handle.AppExceptionHandle;
import com.vaccineadminsystem.service.EmployeeService;
import com.vaccineadminsystem.util.ErrorMess;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
public class JWTAuthenticationControllerTest {

    @MockBean
    private EmployeeService employeeService;

    private MockMvc restMvc;

    private final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private JWTAuthenticationController jwtAuthenticationController;

    @Autowired
    private AppExceptionHandle appExceptionHandle;


    @Before
    public void setUp() {
        this.restMvc =
            MockMvcBuilders.standaloneSetup(jwtAuthenticationController)
                .setControllerAdvice(appExceptionHandle)
                .build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Role roleAdmin = new Role(1, ROLE_ADMIN);
        Employee employee1 =
            new Employee("emp1", new Date(), "ducanh@gmail.com", "duc anh",
                "uname123",
                "$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG",
                "0358705659", roleAdmin);
        when(employeeService.getByUsername("uname123")).thenReturn(employee1);
        String data = "{\n" +
            "    \"username\":\"uname123\",\n" +
            "    \"password\":\"123456789\"\n" +
            "}";
        restMvc.perform(post("/authentication")
            .contentType(MediaType.APPLICATION_JSON)
            .content(data))
            .andExpect(status().isOk());
    }

    @Test
    public void testLoginFailWithWrongCredential() throws Exception {
        Role roleAdmin = new Role(1, ROLE_ADMIN);
        Employee employee1 =
            new Employee("emp1", new Date(), "ducanh@gmail.com", "duc anh",
                "uname123",
                "$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG",
                "0358705659", roleAdmin);
        when(employeeService.getByUsername("uname123")).thenReturn(employee1);
        String data = "{\n" +
            "    \"username\":\"uname11111111\",\n" +
            "    \"password\":\"123456789111111\"\n" +
            "}";
        restMvc.perform(post("/authentication")
            .contentType(MediaType.APPLICATION_JSON)
            .content(data))
            .andExpect(status().isUnauthorized()).
            andExpect(
                jsonPath("$.message").value(ErrorMess.INVALID_CREDENTIAL));
    }

    @Test
    public void testLoginFailWithNullCredential() throws Exception {
        Role roleAdmin = new Role(1, ROLE_ADMIN);
        Employee employee1 =
            new Employee("emp1", new Date(), "ducanh@gmail.com", "duc anh",
                "uname123",
                "$2y$12$tZYgaeBvV5h6yxc/U94K/eAHEj0mL.uHnNH81N6Yqo4z6Pcd27.UG",
                "0358705659", roleAdmin);
        when(employeeService.getByUsername("uname1")).thenReturn(employee1);
        String data = "{\n" +
            "    \"username\":null,\n" +
            "    \"password\":null\n" +
            "}";
        restMvc.perform(post("/authentication")
            .contentType(MediaType.APPLICATION_JSON)
            .content(data))
            .andExpect(status().isBadRequest());
    }


}
