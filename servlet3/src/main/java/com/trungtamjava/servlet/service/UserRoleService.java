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

import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.Repo.UserRoleRepo;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class UserRoleService {
	@Autowired
	UserRoleRepo userRoleRepo;

	@Autowired
	UserRepo userRepo;

	@Transactional
	public void create(UserRoleDTO userRoleDTO) {
		UserRole userRole = new UserRole();
		userRole.setRole(userRoleDTO.getRole());

		User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
		userRole.setUser(user);

		userRoleRepo.save(userRole);
	}

	@Transactional
	public void update(UserRoleDTO userRoleDTO) {
		UserRole userRole = userRoleRepo.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
		userRole.setRole(userRoleDTO.getRole());

		User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
		userRole.setUser(user);

		userRoleRepo.save(userRole);
	}

	@Transactional
	public void delete(int id) {// delete by user role id
		userRoleRepo.deleteById(id);
	}

	@Transactional
	public void deleteAll(List<Integer> ids) {// delete by user role id
		userRoleRepo.deleteAllById(ids);//
	}

	@Transactional
	public void deleteByUserId(int userId) {// delete by user role id
		userRoleRepo.deleteByUserId(userId);
	}

	public UserRoleDTO getById(int id) {
		UserRole userRole = userRoleRepo.findById(id).orElseThrow(NoResultException::new);
		return new ModelMapper().map(userRole, UserRoleDTO.class);
	}

	public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId, pageable);

		PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
		for (UserRole userRole : pageRS.getContent() ) {
			userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
		}

		pageDTO.setContents(userRoleDTOs);
		return pageDTO;
	}

	public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<UserRole> pageRS = userRoleRepo.searchByRole(role, pageable);

		PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
		for (UserRole userRole : pageRS.getContent()) {
			userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
		}

		pageDTO.setContents(userRoleDTOs);// set vao pagedto
		return pageDTO;
	}
}
