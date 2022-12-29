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
import com.trungtamjava.servlet.dto.ScoreDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.CourseService;
import com.trungtamjava.servlet.service.ScoreService;
import com.trungtamjava.servlet.service.StudentService;
import com.trungtamjava.servlet.service.UserRoleService;
@Controller
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	ScoreService scoreService;
	@Autowired
	StudentService studentService;
	@Autowired
	CourseService courseService;

	@GetMapping("/new")
	public String create() {
		return "score/add.html";
	}

	@PostMapping("/new")
	public String add(@ModelAttribute ScoreDTO scoreDTO) {
		scoreService.create(scoreDTO);
		return "redirect:/score/search";
	}

	@GetMapping("/search") //
	public String search(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "score", required = false) Double score,
			@RequestParam(name = "courseId", required = false) Integer courseId,
			@RequestParam(name = "studentId", required = false) Integer studentId,
			@RequestParam(name = "size", required = false) Integer size, 
		@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		
		PageDTO<ScoreDTO> pageRS = null;
		if(id!=null) {
			pageRS = scoreService.searchById(id, page, size);
		}else if (score!=null) {
			
		}
		model.addAttribute("totalPage", pageRS.getTotalPages());
		model.addAttribute("count", pageRS.getTotalElements());
		model.addAttribute("userRoleList", pageRS.getContents());

		// luu lai du lieu set sang view lai
		//model.addAttribute("name", name);
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
