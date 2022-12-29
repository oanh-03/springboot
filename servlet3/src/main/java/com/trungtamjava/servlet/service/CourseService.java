package com.trungtamjava.servlet.service;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.servlet.Repo.CourseRepo;
import com.trungtamjava.servlet.Repo.StudentRepo;
import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.Repo.UserRoleRepo;
import com.trungtamjava.servlet.dto.CourseDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.StudentDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.Course;
import com.trungtamjava.servlet.entity.Student;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class CourseService {
	@Autowired
	CourseRepo courseRepo;
	@Transactional
	public void create(CourseDTO courseDTO) {
		Course course = new ModelMapper()
				.map(courseDTO, Course.class);
		courseRepo.save(course);
	}

	@Transactional
	public void update(CourseDTO courseDTO) {
		Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
		if(course!=null) {
			course.setName(courseDTO.getName());
			courseRepo.save(course);
		}
	}

	@Transactional
	public void delete(int id) {// delete by user role id
		courseRepo.deleteById(id);
	}

	@Transactional
	public void deleteAll(List<Integer> ids) {// delete by user role id
		courseRepo.deleteAllById(ids);//
	}

	@Transactional
	public CourseDTO getById(int id) {// delete by user role id
		Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
		CourseDTO courseDTO = new ModelMapper().map(course, CourseDTO.class);
		return courseDTO;
	}
	public PageDTO<CourseDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Course> pageRS = courseRepo.searchByName(name, pageable);

		PageDTO<CourseDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<CourseDTO> courseDTOs = new ArrayList<>();
		for (Course course : pageRS.getContent() ) {
			courseDTOs.add(new ModelMapper().map(course, CourseDTO.class));
		}

		pageDTO.setContents(courseDTOs);
		return pageDTO;
	}

	
}
