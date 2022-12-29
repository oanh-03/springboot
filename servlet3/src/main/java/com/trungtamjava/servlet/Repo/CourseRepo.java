package com.trungtamjava.servlet.Repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.servlet.entity.Course;
import com.trungtamjava.servlet.entity.Student;

public interface CourseRepo extends JpaRepository<Course, Integer> {
	Page<Course> findById(int id,Pageable pageable);
	
	@Query("SELECT c FROM Course c WHERE c.name LIKE :x")
    Page<Course> searchByName(@Param("x") String s, Pageable pageable);

}
