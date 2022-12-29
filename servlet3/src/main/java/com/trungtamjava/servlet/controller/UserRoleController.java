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

import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.UserRoleService;
@Controller
@RequestMapping("/user-role")
public class UserRoleController {

	@Autowired
	UserRoleService userRoleService;

	@GetMapping("/new")
	public String create() {
		return "userrole/add.html";
	}

	@PostMapping("/new")
	public String add(@ModelAttribute UserRoleDTO userRoleDTO) {
		userRoleService.create(userRoleDTO);
		return "redirect:/user-role/search";
	}

	@GetMapping("/search") //
	public String search(
			@RequestParam(name = "role", required = false) String role,
			@RequestParam(name = "userId", required = false) Integer userId,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		role = role == null ? "%%" : role;
		
		PageDTO<UserRoleDTO> pageRS = null;
		
		if (userId != null)
			pageRS = userRoleService.searchByUserId(userId, page, size);	
		else 
			pageRS = userRoleService.searchByRole(role, page, size);

		model.addAttribute("totalPage", pageRS.getTotalPages());
		model.addAttribute("count", pageRS.getTotalElements());
		model.addAttribute("userRoleList", pageRS.getContents());

		// luu lai du lieu set sang view lai
		model.addAttribute("userId", userId);
		model.addAttribute("role", role);
		model.addAttribute("page", page);
		model.addAttribute("size", size);

		return "userrole/search.html";
	}
	
	@GetMapping("/get/{id}") // get/10
	public String get(@PathVariable("id") int id, Model model) {
		model.addAttribute("userRole", userRoleService.getById(id));
		return "userrole/detail.html";
	}
	
	@GetMapping("/delete") // ?id=1
	public String delete(@RequestParam("id") int id) {
		userRoleService.delete(id);
		return "redirect:/user-role/search";
	}

	@GetMapping("/edit") // ?id=1
	public String edit(@RequestParam("id") int id, Model model) {
		model.addAttribute("userRole", userRoleService.getById(id));
		return "userrole/edit.html";
	}
	@PostMapping("/edit") // ?id=1
	public String edit(@ModelAttribute UserRoleDTO userRoleDTO) {
		userRoleService.update(userRoleDTO);
		return "redirect:/user-role/search";
	}
}
