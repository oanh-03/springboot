package com.trungtamjava.servlet.rest;
import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.ResponseDTO;
import com.trungtamjava.servlet.dto.UserDTO;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
@RestController
@RequestMapping("/api/user/")
public class RESTUserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/upload-multi")
	public void add(@RequestParam("files") MultipartFile[] files) throws IllegalStateException, IOException {
		System.out.println(files.length);
		for (MultipartFile file : files)
			if (!file.isEmpty()) {
				final String UPLOAD_FOLDER = "Desktop:/file/";

				String filename = file.getOriginalFilename();
				File newFile = new File(UPLOAD_FOLDER + filename);

				file.transferTo(newFile);
			}

	}
	@PostMapping("/new")
	public ResponseDTO<UserDTO> add(@ModelAttribute UserDTO u) throws IllegalStateException, IOException {

		if (u.getFile() != null && !u.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "Desktop:/file/";

			String filename = u.getFile().getOriginalFilename();
			File newFile = new File(UPLOAD_FOLDER + filename);

			u.getFile().transferTo(newFile);

			u.setAvatar(filename);// save to db
		}
		// goi qua service
		userService.create(u);
//		ResponseDTO<Void> responseDTO = new ResponseDTO<>();
//		responseDTO.setStatus(200);
//		return responseDTO;
		return ResponseDTO.<UserDTO>builder().status(200)
				.data(u).build();
		}
	@GetMapping("/download")
	public void dowload(@RequestParam("filename")String filename,
			HttpServletResponse response) throws IOException{
		final String UPLOAD_FOLDER = "Desktop:/file/";
		File file = new File(UPLOAD_FOLDER+filename);
		
		Files.copy(file.toPath(), response.getOutputStream());
	}
	@GetMapping("/search") //
	public ResponseDTO<PageDTO<UserDTO>> search(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = false) String name,

			@RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
			@RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;
		name = name == null ? "" : name;

		PageDTO<UserDTO> pageRS = userService.searchByName("%" + name + "%", page, size);
//		ResponseDTO<PageDTO<UserDTO>> responseDTO = new ResponseDTO<>();
//		responseDTO.setStatus(200);
//		responseDTO.setData(pageRS);
//		return responseDTO;
		return ResponseDTO.<PageDTO<UserDTO>>builder()
				.status(200).data(pageRS).build();
	}
	@GetMapping("/get/{id}") // get/10
	public ResponseDTO<UserDTO> get(@PathVariable("id") int id) {
		UserDTO user = userService.getById(id);
		return ResponseDTO.<UserDTO>builder()
				.status(200)
				.data(user).build();
//		ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
//		responseDTO.setStatus(200);
//		responseDTO.setData(user);
//		return responseDTO;
	}

	@DeleteMapping("/delete") // ?id=1
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.delete(id);
//		ResponseDTO<Void> responseDTO = new ResponseDTO<>();
//		responseDTO.setStatus(200);
//		return responseDTO;
		return ResponseDTO.<Void>builder()
				.status(200)
				.build();
	}


	@PostMapping("/edit")
	public ResponseDTO<Void> edit(@ModelAttribute UserDTO editUser) throws IllegalStateException, IOException {

		if (!editUser.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/";

			String filename = editUser.getFile().getOriginalFilename();
			File newFile = new File(UPLOAD_FOLDER + filename);

			editUser.getFile().transferTo(newFile);

			editUser.setAvatar(filename);// save to db
		}

		// lay du lieu can update tu edit qua current, de tranh mat du lieu cu
		userService.update(editUser);

//		ResponseDTO<Void> responseDTO = new ResponseDTO<>();
//		responseDTO.setStatus(200);
//		return responseDTO;
		return ResponseDTO.<Void>builder()
				.status(200)
				.build();
	}
}


