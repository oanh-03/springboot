package com.trungtamjava.servlet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Data;

@Entity
@Data
public class Student {
	@Id
	private Integer id;

	@Column(unique = true)
	private String studentCode;

	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;

}