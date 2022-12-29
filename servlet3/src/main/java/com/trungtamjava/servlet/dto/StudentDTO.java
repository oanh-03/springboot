package com.trungtamjava.servlet.dto;




import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trungtamjava.servlet.entity.User;

import lombok.Data;
@Data
public class StudentDTO {

	private Integer id;
	private String studentCode;
	private UserDTO user;

	
	
}
