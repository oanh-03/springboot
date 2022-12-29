package com.trungtamjava.servlet.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.trungtamjava.servlet.Repo.StudentRepo;
import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.StudentDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.Student;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class StudentService {
	@Autowired
	StudentRepo studentRepo;

	@Autowired
	UserRepo userRepo;

	@Transactional
	@CacheEvict(cacheNames = "students", allEntries = true)

	public void create(StudentDTO studentDTO) {
		User user = userRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
		for (UserRole userRole : user.getUserRoles()) {
			if (userRole.getRole().equals("ROLE_STUDENT")) {
				Student student = new Student();
				student.setStudentCode(studentDTO.getStudentCode());
				student.setId(studentDTO.getId());

				studentRepo.save(student);
				return;// kthuc
			}
		}

	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "students", allEntries = true),
			@CacheEvict(cacheNames = "users", allEntries = true)
	})

	public void update(StudentDTO studentDTO) {
		Student s = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
			if(s!=null) {
				s.setStudentCode(studentDTO.getStudentCode());
			studentRepo.save(s);
			}
	}

	@Transactional
	public void delete(int id) {// delete by user role id
		studentRepo.deleteById(id);
	}

	@Transactional
	public void deleteAll(List<Integer> ids) {// delete by user role id
		studentRepo.deleteAllById(ids);//
	}

	@Transactional
	public StudentDTO getById(int id) {
		Student s = studentRepo.findById(id).orElseThrow(NoResultException::new);
		return new ModelMapper().map(s, StudentDTO.class);
	}

	@Transactional
	public void deleteById(int userId) {
		Student s = studentRepo.findById(userId).orElseThrow(NoResultException::new);
		studentRepo.deleteById(userId);
	}

	@Cacheable(cacheNames = "students") // key - value
	public PageDTO<StudentDTO> search(String name, String studentCode, int page, int size) {
		org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
		Page<Student> pageRS = null;
		if (StringUtils.hasText(name) && StringUtils.hasText(studentCode)) {
			pageRS = studentRepo.searchByNameAndCode(name, studentCode, pageable);
		} else if (StringUtils.hasText(name)) {
			pageRS = studentRepo.searchByName(name, pageable);
		} else if (StringUtils.hasText(studentCode)) {
			pageRS = studentRepo.searchByCode(name, pageable);
		} else {
			pageRS = studentRepo.findAll(pageable);
		}
		PageDTO<StudentDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<StudentDTO> studentDTOs = new ArrayList<>();
		for (Student student : pageRS.getContent())
			studentDTOs.add(new ModelMapper().map(student, StudentDTO.class));

		pageDTO.setContents(studentDTOs);
		return pageDTO;
	}

}
