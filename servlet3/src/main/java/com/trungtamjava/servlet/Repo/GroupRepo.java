package com.trungtamjava.servlet.Repo;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trungtamjava.servlet.entity.Group;
import com.trungtamjava.servlet.entity.Student;

public interface GroupRepo extends JpaRepository<Group, Integer> {
	@Modifying
	@Query("SELECT g FROM Group g WHERE g.name =:x")
	public void deleteByName(@Param("x") String name);
	
	@Query("SELECT g FROM Group g WHERE g.id =:x")
	Page<Group> searchById(@Param("x")int id, org.springframework.data.domain.Pageable pageable);
	
	@Query("SELECT g FROM Group g JOIN g.users u WHERE u.name LIKE :x")
	Page<Group> searchByName(@Param("x") String name,org.springframework.data.domain.Pageable pageable);
	
	
	@Query("SELECT u FROM User u JOIN u.groups g WHERE u.id LIKE :x")
	Page<Group> searchByUser(@Param("x") int id,org.springframework.data.domain.Pageable pageable);

	
	
}
