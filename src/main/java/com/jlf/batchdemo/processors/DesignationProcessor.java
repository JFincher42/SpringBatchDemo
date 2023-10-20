package com.jlf.batchdemo.processors;

import com.jlf.batchdemo.models.Designation;
import com.jlf.batchdemo.exceptions.EntityNotFoundException;
import com.jlf.batchdemo.models.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DesignationProcessor implements ItemProcessor<Employee, Employee> {
    // This helps you to convert the designations of the employees into the Enum you defined earlier
    @Override
    public Employee process(Employee employee) throws EntityNotFoundException {
        employee.setDesignation(Designation.getByCode(employee.getDesignation()).getTitle());
        employee.setDesignationUpdatedAt(new Date());
        return employee;
    }
}
