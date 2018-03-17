package com.java.springboot.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableBatchProcessing
@ComponentScan({"com.java.springboot.demo"})
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

	    SpringApplication.run(DemoApplication.class, args);
	}
}
