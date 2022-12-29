package com.trungtamjava.servlet.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class GroupDTO {
	private Integer id;
	@NotBlank
	@Size(min=6)
	private String name;
	List<UserDTO> users;
}
