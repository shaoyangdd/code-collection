package com.ksf.codecollection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@SpringBootConfiguration
@ComponentScan(basePackages = {"com.ksf.codecollection.controller","com.ksf.codecollection.service"})
public class CodeCollectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeCollectionApplication.class, args);
	}
}
