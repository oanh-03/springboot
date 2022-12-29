package com.trungtamjava.servlet.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.servlet.dto.CourseDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.ResponseDTO;
import com.trungtamjava.servlet.dto.StudentDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.CourseService;
import com.trungtamjava.servlet.service.UserRoleService;
@RestController
@RequestMapping("api/course/")
public class RESTCourseController {

	@Autowired
	CourseService courseService;

	
	@PostMapping("/new")
	public ResponseDTO<CourseDTO> add(@ModelAttribute CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return ResponseDTO.<CourseDTO>builder().status(200)
				.data(courseDTO).build();
	}

	@PostMapping("/new-json")
	public ResponseDTO<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return ResponseDTO.<CourseDTO>builder().status(200)
				.data(courseDTO).build();
	}

	@GetMapping("/search") //
	public ResponseDTO<PageDTO<CourseDTO>> search(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		
		PageDTO<CourseDTO> pageRS = null;
		pageRS = courseService.searchByName(name, page, size);

		return ResponseDTO.<PageDTO<CourseDTO>>builder().status(200)
			.data(pageRS).build();
		}
	
	@GetMapping("/get/{id}") // get/10
	public ResponseDTO<CourseDTO> get(@PathVariable("id") int id, Model model) {
		CourseDTO courseDTO = courseService.getById(id);
		return ResponseDTO.<CourseDTO>builder().status(200)
				.data(courseDTO).build();
	}
	
	@GetMapping("/delete") // ?id=1
	public  ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder().status(200)
				.build();
	}

	@PostMapping("/edit")
	public ResponseDTO<CourseDTO> edit(@ModelAttribute CourseDTO courseDTO ) throws IllegalStateException,IOException {
		courseService.update(courseDTO);
		return ResponseDTO.<CourseDTO>builder()
				.status(200).data(courseDTO)
				.build();		
	} 
}
