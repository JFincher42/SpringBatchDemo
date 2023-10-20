package com.jlf.batchdemo.processors;

import com.jlf.batchdemo.models.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NameProcessor implements ItemProcessor<Employee, Employee> {
    // This helps you to process the names of the employee at a set time
    @Override
    public Employee process(Employee employee) {
        employee.setName(employee.getName().toUpperCase());
        employee.setNameUpdatedAt(new Date());
        return employee;
    }
}
