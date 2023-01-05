package com.project.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.CategoryDTO;
import com.project.dto.PageDTO;
import com.project.dto.ResponseDTO;
import com.project.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	// jackson
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseDTO<CategoryDTO> add(@RequestBody @Valid CategoryDTO category) {
		categoryService.create(category);
		return ResponseDTO.<CategoryDTO>builder().status(200).data(category).build();
	}

	@GetMapping("/search")
	public ResponseDTO<PageDTO<CategoryDTO>> search(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page
			) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "" : name;

		PageDTO<CategoryDTO> pageRS = 
				categoryService.searchByName("%" + name + "%", page, size);

		return ResponseDTO.<PageDTO<CategoryDTO>>builder()
				.status(200)
				.data(pageRS).build();
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<CategoryDTO> get(@PathVariable("id") int id) {
		CategoryDTO categoryDTO = categoryService.getById(id);
		return ResponseDTO.<CategoryDTO>builder().status(200).data(categoryDTO).build();
	}

	@DeleteMapping("/{id}") // /1
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		categoryService.delete(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid CategoryDTO category) {
		categoryService.update(category);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
