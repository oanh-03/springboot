package com.trungtamjava.servlet.controller;




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.servlet.PersonService;
import com.trungtamjava.servlet.Repo.PersonRepo;
import com.trungtamjava.servlet.entity.Laptop;
import com.trungtamjava.servlet.entity.Person;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Controller
@RequestMapping("/person")
public class PersonController {
	List<Person> persons = new ArrayList<>();
	@Autowired
	PersonRepo personRepo;
	
	@Autowired
	Laptop laptop;
	@Autowired
	PersonService personSer;
	@GetMapping("/create")
	public String create() {
		System.out.println(laptop.getName());
		return "person/create.html";
	}
	@PostMapping("/create")
	public String create(HttpServletRequest req,
		@RequestParam("id") int id,
		@RequestParam("name") String name,
		@RequestParam("age") int age
		){
		//String id = req.getParameter("id");
		Person p = new Person();
		p.setId(id);
		p.setName(name);
		p.getAge();
		persons.add(p);
		//save to db
		personRepo.save(p);
		//chuyen huong redirect
		
		
		return "redirect:/person/list";
	}
	@GetMapping("/list")
	public String list(Model model) {
		List<Person> persons = personSer.getAll();
		model.addAttribute("list", persons);
		return "person/list.html";
		
	}
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		personRepo.deleteById(id);
		return "redirect:/person/list"; 
		
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Person p = personRepo.findById(id).orElse(null);
		model.addAttribute("person", p);
		return "/person/edit.html"; 
		
	}
	@PostMapping("/edit")
	public String edit(@ModelAttribute Person p){
		personRepo.save(p);
		//chuyen huong redirect
		return "redirect:/person/list";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("min") int min,
			@RequestParam("max") int max,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			Model model) {
		
		Page<Person> pagePerson = personRepo.search(min, max,PageRequest.of(page,size));
		System.out.println(pagePerson.getTotalPages());
		System.out.println(pagePerson.getNumberOfElements());
		model.addAttribute("list", pagePerson.getContent());
		return "person/list.html";
		
	}
}

