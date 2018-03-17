package com.java.springboot.demo.jobs;

import com.java.springboot.demo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuildFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<User> createWorkBookReader(){
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<User>();
        itemReader.setResource(new FileSystemResource("src/main/resources/Workbook.csv"));
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"Name", "Address", "Postcode", "Phone" ,"Credit Limit", "Birthday"});
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>();
        lineMapper.setLineTokenizer(tokenizer);
        itemReader.setLinesToSkip(1);
        lineMapper.setFieldSetMapper(new UserFieldSetMapper());
        itemReader.setLineMapper(lineMapper);
        itemReader.open(new ExecutionContext());
        return itemReader;
    }


    @Bean
    public JdbcBatchItemWriter<User> workBookDBWriter() {
        JdbcBatchItemWriter<User> workBookDBWriter = new JdbcBatchItemWriter<User>();
        workBookDBWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
        workBookDBWriter.setSql("INSERT INTO USERS (name, surname, address, postcode, creditlimit, phone, birthday) VALUES (:name, :surname, :address, :postCode, :creditLimit, :phone, :birthday)");
        workBookDBWriter.setDataSource(dataSource);
        return workBookDBWriter;
    }

    @Bean
    public Step loadUsersWorkBookStep() {
        return stepBuildFactory.get("loadUsersWorkBook")
                .<User, User>chunk(1)
                .reader(createWorkBookReader())
                .writer(workBookDBWriter())
                .build();
    }

    @Bean
    public Job initializeDataJob() {
        return jobBuilderFactory.get("initializeDataJob")
                .incrementer(new RunIdIncrementer())
                .flow(loadUsersWorkBookStep())
                .end()
                .build();
    }
}
