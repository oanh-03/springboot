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
import com.trungtamjava.servlet.Repo.ScoreRepo;
import com.trungtamjava.servlet.Repo.StudentRepo;
import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.Repo.UserRoleRepo;
import com.trungtamjava.servlet.dto.CourseDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.ScoreDTO;
import com.trungtamjava.servlet.dto.StudentDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.Course;
import com.trungtamjava.servlet.entity.Score;
import com.trungtamjava.servlet.entity.Student;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class ScoreService {
	@Autowired
	ScoreRepo scoreRepo;
	@Autowired
	CourseRepo courseRepo;
	@Autowired
	StudentRepo studentRepo;
	@Transactional
	public void create(ScoreDTO scoreDTO) {
		Score score = new Score();
		score.setScore(scoreDTO.getScore());
		CourseDTO courseDTO = scoreDTO.getCourse();
		Course course = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
		score.setCourse(course);
		StudentDTO studentDTO = scoreDTO.getStudent();
		Student student = studentRepo.findById(studentDTO.getId()).orElseThrow(NoResultException::new);
		score.setStudent(student);
		scoreRepo.save(score);
	}

	@Transactional
	public void update(ScoreDTO scoreDTO) {
		Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
		if(score!=null) {
			score.setScore(scoreDTO.getScore());
			scoreRepo.save(score);
		}
	}

	@Transactional
	public void deleteById(int id) {// delete by user role id
		Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
		if(score!=null) {
			scoreRepo.deleteById(id);
		}
	}

	@Transactional
	public void deleteAll(List<Integer> ids) {// delete by user role id
		courseRepo.deleteAllById(ids);//
	}

	@Transactional
	public ScoreDTO getById(int id) {// delete by user role id
		Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
		return new ModelMapper().map(score, ScoreDTO.class);
	}
	public PageDTO<ScoreDTO> searchById(Integer id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Score> pageRS = scoreRepo.searchById(id, pageable);
		
		PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<ScoreDTO> scoreDTOs = new ArrayList<>();
		for (Score x : pageRS.getContent() ) {
			scoreDTOs.add(new ModelMapper().map(x, ScoreDTO.class));
		}
		
		pageDTO.setContents(scoreDTOs);
		return pageDTO;
	}
	public PageDTO<ScoreDTO> searchByCouseId(Integer id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Score> pageRS = scoreRepo.searchByCourseId(id, pageable);
		
		PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<ScoreDTO> scoreDTOs = new ArrayList<>();
		for (Score x : pageRS.getContent() ) {
			scoreDTOs.add(new ModelMapper().map(x, ScoreDTO.class));
		}
		
		pageDTO.setContents(scoreDTOs);
		return pageDTO;
	}
	public PageDTO<ScoreDTO> searchByStudentId(Integer id, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Score> pageRS = scoreRepo.searchByStudentId(id, pageable);
		
		PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		
		List<ScoreDTO> scoreDTOs = new ArrayList<>();
		for (Score x : pageRS.getContent() ) {
			scoreDTOs.add(new ModelMapper().map(x, ScoreDTO.class));
		}
		
		pageDTO.setContents(scoreDTOs);
		return pageDTO;
	}
	public PageDTO<ScoreDTO> searchByScore(Double score, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Score> pageRS = scoreRepo.searchByScore(score, pageable);

		PageDTO<ScoreDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<ScoreDTO> scoreDTOs = new ArrayList<>();
		for (Score x : pageRS.getContent() ) {
			scoreDTOs.add(new ModelMapper().map(x, ScoreDTO.class));
		}

		pageDTO.setContents(scoreDTOs);
		return pageDTO;
	}

	
}
