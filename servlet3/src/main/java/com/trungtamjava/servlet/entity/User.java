package com.trungtamjava.servlet.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	private String name;

	@Column(name = "avatar")
	private String avatar;// URL

	@Column(unique = true)
	private String username;
	private String password;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthdate;

	@Transient // field is not persistent.
	private MultipartFile file;

	@CreatedDate // tu gen
//	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(updatable = false)
	private Date createdAt;
	
	@LastModifiedDate
	private Date lastUpdateAt;
	
//	@ElementCollection
//	@CollectionTable(name = "user_role", 
//		joinColumns = @JoinColumn(name = "user_id"))
//	@Column(name = "role")
//	private List<String> roles;

	//ko bat buoc
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UserRole> userRoles;
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private List<Group> groups;
	

	
	
}
