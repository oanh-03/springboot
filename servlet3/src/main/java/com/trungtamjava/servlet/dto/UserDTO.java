package com.trungtamjava.servlet.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {
	public Integer id;
	
	@NotBlank
	private String name;
	
	private String avatar;// URL
	private String username;
	private String password;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;
	@JsonIgnore
	private MultipartFile file;
	private Date createdAt;
//	@JsonManagedReference
	private List<UserRoleDTO> userRoles;
	}
