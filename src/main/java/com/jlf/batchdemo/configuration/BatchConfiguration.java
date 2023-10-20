package com.jlf.batchdemo.configuration;

import com.jlf.batchdemo.*;
import com.jlf.batchdemo.models.Employee;
import com.jlf.batchdemo.processors.DesignationProcessor;
import com.jlf.batchdemo.processors.NameProcessor;
import com.jlf.batchdemo.repositories.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step nameStep, Step designationStep)
    {
        return new JobBuilder("employee-loader-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(nameStep)
                .next(designationStep)
                .build();
    }

    @Bean
    public Step nameStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<Employee> csvReader, NameProcessor processor, EmployeeWriter writer){
        return new StepBuilder("name-step", jobRepository)
                .<Employee, Employee>chunk(100, transactionManager)
                .reader(csvReader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(false)
                .build();
    }

    @Bean
    public Step designationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<Employee> repositoryReader, DesignationProcessor processor, EmployeeWriter writer) {
        // This step reads the data from the database and then converts the designation into the matching Enums.
        return new StepBuilder("designation-step", jobRepository)
                .<Employee, Employee>chunk(100, transactionManager)
                .reader(repositoryReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<Employee> csvReader(@Value("${inputFile}") String inputFile) {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("csv-reader")
                .resource(new ClassPathResource(inputFile))
                .delimited()
                .names("id", "name", "designation")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{setTargetType(Employee.class);}})
                .build();
    }

    @Bean
    public RepositoryItemReader<Employee> repositoryReader(EmployeeRepository employeeRepository) {
        return new RepositoryItemReaderBuilder<Employee>()
                .repository(employeeRepository)
                .methodName("findAll")
                .sorts(Map.of("id", Sort.Direction.ASC))
                .name("repository-reader")
                .build();
    }
}

