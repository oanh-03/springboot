package com.trungtamjava.servlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trungtamjava.servlet.dto.CourseDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.CourseService;
import com.trungtamjava.servlet.service.UserRoleService;
@Controller
@RequestMapping("/course")
public class CourseController {

	@Autowired
	CourseService courseService;

	@GetMapping("/new")
	public String create() {
		return "userrole/add.html";
	}

	@PostMapping("/new")
	public String add(@ModelAttribute CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return "redirect:/course/search";
	}

	@GetMapping("/search") //
	public String search(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "%%" : name;
		
		PageDTO<CourseDTO> pageRS = null;
		
		model.addAttribute("totalPage", pageRS.getTotalPages());
		model.addAttribute("count", pageRS.getTotalElements());
		model.addAttribute("userRoleList", pageRS.getContents());

		// luu lai du lieu set sang view lai
		model.addAttribute("name", name);
		model.addAttribute("page", page);
		model.addAttribute("size", size);

		return "course/search.html";
	}
	
	@GetMapping("/get/{id}") // get/10
	public String get(@PathVariable("id") int id, Model model) {
		model.addAttribute("userRole", courseService.getById(id));
		return "course/detail.html";
	}
	
	@GetMapping("/delete") // ?id=1
	public String delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return "redirect:/course/search";
	}

	@GetMapping("/edit") // ?id=1
	public String edit(@RequestParam("id") int id, Model model) {
		model.addAttribute("course", courseService.getById(id));
		return "course/edit.html";
	}
	@PostMapping("/edit") // ?id=1
	public String edit(@ModelAttribute CourseDTO courseDTO) {
		courseService.update(courseDTO);
		return "redirect:/course/search";
	}
}
