package com.trungtamjava.servlet.Repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.servlet.entity.Person;

public interface PersonRepo
extends JpaRepository<Person, Integer>{

	@Query("SELECT p FROM Person p WHERE p.age >= :min AND p.age<= :max")
	List<Person> search(@Param("min") int min, @Param("max")int max);
	
	//tu gen lenh - where name=
	List<Person> findByName(String name);
	//phan trang
	Page<Person> findByName(String name,Pageable page);
	@Query("SELECT p FROM Person p WHERE p.age >= :min AND p.age<= :max")
	Page<Person> search(@Param("min") int min, @Param("max")int max,Pageable page);
	
	
	
}
