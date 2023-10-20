package com.jlf.batchdemo.repositories;

import com.jlf.batchdemo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
