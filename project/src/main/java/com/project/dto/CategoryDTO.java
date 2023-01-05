package com.project.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryDTO {
private Integer id;
@NotBlank
@Size(min=6,max=20)
private String name;
}
