package com.project.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String avatar;// URL

	@Column(unique = true)
	private String username;
	
	private String password;
	
	private String email;

	private Date birthdate;

	@CreatedDate // tu gen
	@Column(updatable = false)
	private Date createdAt;

	@ElementCollection
	@CollectionTable(name = "user_role", 
		joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<String> roles;

}
