package com.trungtamjava.servlet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
@RequestMapping("/api")
public class HelloController {
	@GetMapping("/hello-spring")
	public String hello(Model model) {
		model.addAttribute("msg","Hello Spring Framework");
		return "hi.html";
	}
}
