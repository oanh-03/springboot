package com.trungtamjava.servlet.rest;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.ResponseDTO;
import com.trungtamjava.servlet.dto.StudentDTO;
import com.trungtamjava.servlet.dto.UserDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class RESTStudentController {
	@Autowired
	StudentService studentService;
	
	@PostMapping("/new")
	public ResponseDTO<StudentDTO> add(@RequestBody StudentDTO studentDTO){
		studentService.create(studentDTO);

		return ResponseDTO.<StudentDTO>builder()
				.status(200)
				.data(studentDTO).build();
	}
	@PostMapping("/new-json")//JSON
	public ResponseDTO<StudentDTO> create(@RequestBody StudentDTO studentDTO) {
		studentService.create(studentDTO);
		return ResponseDTO.<StudentDTO>builder()
				.status(200)
				.data(studentDTO).build();
	}
	@DeleteMapping("/delete") // ?id=1 
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		studentService.deleteById(id);
		return ResponseDTO.<Void>builder()
				.status(200)
				.build();

	}
	
	@GetMapping("/get/{id}") // get/10
	public ResponseDTO<StudentDTO> get(@PathVariable("id") int id) {
		StudentDTO studentDTO = studentService.getById(id);
		return ResponseDTO.<StudentDTO>builder()
				.status(200).data(studentDTO)
				.build();		
	}
	@PostMapping("/search") //
	public ResponseDTO<PageDTO<StudentDTO>> search(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "studentCode", required = false) String studentCode,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		studentCode = studentCode == null ?"":studentCode;
		name = name == null?"":name;

		PageDTO<StudentDTO> pageRS = null;
		return ResponseDTO.<PageDTO<StudentDTO>>builder()
				.status(200).data(studentService.search(name, studentCode, page, size)).build();
	}
	@PostMapping("/edit")
	public ResponseDTO<StudentDTO> edit(@ModelAttribute StudentDTO studentDTO ) throws IllegalStateException,IOException {
		studentService.update(studentDTO);
		return ResponseDTO.<StudentDTO>builder()
				.status(200).data(studentDTO)
				.build();		
	} 
	
}
