package com.trungtamjava.servlet.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.UserRoleService;

@RestController
@RequestMapping("/api/user-role/")
public class UserRoleRESTController {

	@Autowired
	UserRoleService userRoleService;

	@PostMapping("/new")
	public void add(@ModelAttribute UserRoleDTO userRoleDTO) {
		userRoleService.create(userRoleDTO);
	}
	@PostMapping("/new-json")//JSON
	public void create(@RequestBody UserRoleDTO userRoleDTO) {
		userRoleService.create(userRoleDTO);
	}
	
	@DeleteMapping("/delete") // ?id=1 
	public void delete(@RequestParam("id") int id) {
		userRoleService.delete(id);

	}
	
	@GetMapping("/get/{id}") // get/10
	public UserRoleDTO get(@PathVariable("id") int id) {
		return userRoleService.getById(id);//jackson
		
	}
	@PostMapping("/search") //
	public PageDTO<UserRoleDTO> search(
			@RequestParam(name = "role", required = false) String role,
			@RequestParam(name = "userId", required = false) Integer userId,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		role = role == null ? "%%" : role;
		
		PageDTO<UserRoleDTO> pageRS = null;
		
		if (userId != null)
			pageRS = userRoleService.searchByUserId(userId, page, size);	
		else 
			pageRS = userRoleService.searchByRole(role, page, size);



		return pageRS;
	}

}
