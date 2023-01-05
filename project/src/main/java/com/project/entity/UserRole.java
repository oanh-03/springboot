//package com.project.entity;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//
//import lombok.Data;
//
////@RequestBody
//// {
////"user": {
////    "id" : 11
////},
////"role":"ADMIN"
////
////}
//@Entity
//@Data
//public class UserRole {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@ManyToOne
//	@JsonBackReference
//	private User user;
//
//	private String role;// ADMIN,MEMBER
//}
