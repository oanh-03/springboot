package com.trungtamjava.servlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	@Autowired
	MessageSource mess;
	@GetMapping("/hello")
	public String hello() {
		System.out.println(mess.getMessage("msg.hello", null,null));
		return "hello.html";
	}
	
}
