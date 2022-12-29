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
import com.trungtamjava.servlet.dto.GroupDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.service.CourseService;
import com.trungtamjava.servlet.service.GroupService;
import com.trungtamjava.servlet.service.UserRoleService;
@Controller
@RequestMapping("/group")
public class GroupController {

	@Autowired
	GroupService groupService;

	@GetMapping("/new")
	public String create() {
		return "group/add.html";
	}

	@PostMapping("/new")
	public String add(@ModelAttribute GroupDTO groupDTO) {
		groupService.create(groupDTO);
		return "redirect:/group/search";
	}

	@GetMapping("/search") //
	public String search(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "userId", required = false) Integer userId,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "%%" : name;
		userId = userId == null ? null : userId;
		
		PageDTO<GroupDTO> pageRS = groupService.searchByName("%" + name + "%", page, size);

		model.addAttribute("totalPage", pageRS.getTotalPages());
		model.addAttribute("count", pageRS.getTotalElements());
		model.addAttribute("userRoleList", pageRS.getContents());

		// luu lai du lieu set sang view lai
		model.addAttribute("id", name);
		model.addAttribute("name", name);
		model.addAttribute("userId", name);
		model.addAttribute("page", page);
		model.addAttribute("size", size);

		return "group/search.html";
	}
	
	@GetMapping("/get/{id}") // get/10
	public String get(@PathVariable("id") int id, Model model) {
		model.addAttribute("group", groupService.getById(id));
		return "group/detail.html";
	}
	
	@GetMapping("/delete") // ?id=1
	public String delete(@RequestParam("id") int id) {
		groupService.delete(id);
		return "redirect:/group/search";
	}

	@GetMapping("/edit") // ?id=1
	public String edit(@RequestParam("id") int id, Model model) {
		model.addAttribute("course", groupService.getById(id));
		return "group/edit.html";
	}
	@PostMapping("/edit") // ?id=1
	public String edit(@ModelAttribute GroupDTO groupDTO) {
		groupService.update(groupDTO);
		return "redirect:/group/search";
	}
}
