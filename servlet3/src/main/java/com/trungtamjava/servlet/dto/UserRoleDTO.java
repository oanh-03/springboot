package com.trungtamjava.servlet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trungtamjava.servlet.entity.User;

import lombok.Data;

@Data
public class UserRoleDTO {
	private Integer id;

	private Integer userId;

	private String userName;
	
	private String role;
//	@JsonIgnoreProperties("userRoles")
//	@JsonBackReference
//	private UserDTO user;
}
