package com.example.demo;

import com.example.demo.model.Emp;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
public class Application {
	@Autowired
	EnpRepo enpRepo;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
