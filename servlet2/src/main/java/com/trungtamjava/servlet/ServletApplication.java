package com.trungtamjava.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.trungtamjava.servlet.entity.Laptop;

@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
	//spring container
	//bean : obj :Ioc
@Bean	
	public Laptop laptop1() {
		Laptop laptop = new Laptop();
		laptop.setId(1);
		laptop.setName("A");
		return laptop;
	}

}
