package com.jlf.batchdemo;

import com.jlf.batchdemo.models.Employee;
import com.jlf.batchdemo.repositories.EmployeeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EmployeeWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${sleepTime}")
    private Integer SLEEP_TIME;

    @Override
    public void write(@NonNull Chunk<? extends Employee> employees) throws InterruptedException {
        employeeRepository.saveAll(employees);
        Thread.sleep(SLEEP_TIME);
        System.out.println("Saved employees: " + employees);
    }

//    @Override
//    public void write(Chunk<? extends Employee> chunk) throws Exception {
//
//    }
}
