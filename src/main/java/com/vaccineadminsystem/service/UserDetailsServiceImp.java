package com.vaccineadminsystem.service;

import com.vaccineadminsystem.dto.EmployeeDto;
import com.vaccineadminsystem.dto.JWTUserDetails;
import com.vaccineadminsystem.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private ModelMapper modelMapper;

    /**
     * find by username
     * @param s: username input in client
     * @return a user details exist from db
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) {
        Employee employee = employeeService.getByUsername(s);
        if (employee == null) {
            throw new UsernameNotFoundException(s);
        } else {
            return new JWTUserDetails(
                    employee.getEmployeeId(),
                    employee.getUsername(),
                    employee.getPassword(),
                    Arrays.asList(employee.getRole().getName()),
                    modelMapper.map(employee, EmployeeDto.class)
            );
        }
    }
}
