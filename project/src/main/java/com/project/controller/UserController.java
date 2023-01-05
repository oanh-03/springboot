package com.project.controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.PageDTO;
import com.project.dto.ResponseDTO;
import com.project.dto.UserDTO;
import com.project.service.UserService;



@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/")
	public ResponseDTO<UserDTO> add(@ModelAttribute @Valid UserDTO u) throws IllegalStateException, IOException {
		if (u.getFile() != null && !u.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/user/";

			String filename = u.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;

			File newFile = new File(UPLOAD_FOLDER + newFilename);

			u.getFile().transferTo(newFile);

			u.setAvatar(filename);// save to db
		}

//		u.setPassword(new BCryptPasswordEncoder()
//				.encode(u.getPassword()));
		userService.create(u);
		return ResponseDTO.<UserDTO>builder().status(200).data(u).build();
	}

	/// /user/download/abc.jpg
	@GetMapping("/download/{filename}")
	public void download(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
		final String UPLOAD_FOLDER = "D:/file/user/";
		File file = new File(UPLOAD_FOLDER + filename);
		Files.copy(file.toPath(), response.getOutputStream());
	}

	@GetMapping("/search")
//	@Secured({"Admin"})
//	@PreAuthorize("hasAuthority('Admin')")
//	@PostAuthorize("returnObject != null || #id != null")
	public ResponseDTO<PageDTO<UserDTO>> search(
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "name", required = false) String name,

			@RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
			@RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page, Model model) {

		size = size == null ? 10 : size;
		page = page == null ? 0 : page;

		PageDTO<UserDTO> pageRS = userService.searchByName("%" + name + "%", page, size);

		return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).data(pageRS).build();
	}

	@GetMapping("/{id}") // 10
	public ResponseDTO<UserDTO> get(@PathVariable("id") int id) {
		UserDTO userDTO = userService.getById(id);
		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
	}

	@DeleteMapping("/{id}") // /1
	public ResponseDTO<Void> delete(@PathVariable("id") int id) {
		userService.delete(id);
		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> update(@ModelAttribute @Valid UserDTO u) throws IllegalStateException, IOException {
		if (u.getFile() != null && !u.getFile().isEmpty()) {
			final String UPLOAD_FOLDER = "D:/file/user/";

			String filename = u.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;

			File newFile = new File(UPLOAD_FOLDER + newFilename);

			u.getFile().transferTo(newFile);

			u.setAvatar(filename);// save to db
		}

		userService.update(u);

		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PutMapping("/password")
	public ResponseDTO<Void> updatePassword(
			@RequestBody @Valid UserDTO u) {
		userService.updatePassword(u);
		return ResponseDTO.<Void>builder().status(200).build();
	}
}
