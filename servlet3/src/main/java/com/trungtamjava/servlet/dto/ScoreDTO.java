package com.trungtamjava.servlet.dto;

import java.util.List;

import lombok.Data;

@Data
public class ScoreDTO {
	private Integer id;
	private double score;
	private StudentDTO student;
	private CourseDTO course;
}
