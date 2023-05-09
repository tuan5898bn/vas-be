package com.vaccineadminsystem.repository;

import com.vaccineadminsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("delete from Employee e where e.employeeId in :ids")
    @Modifying
    void deleteEmployeesWithIds(@Param("ids") List<String> ids);
}
