package com.trungtamjava.servlet.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.Repo.UserRoleRepo;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserDTO ;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;

	@Autowired
	UserRoleRepo userRoleRepo;
	
	@Autowired
	CacheManager cacheManager;
	
	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class);
		// convert dto -> entity
//		user.setName(userDTO.getName());
//		user.setUsername(userDTO.getUsername());
//		user.setBirthdate(userDTO.getBirthdate());
//		user.setPassword(userDTO.getPassword());
//		user.setAvatar(userDTO.getAvatar());

//		for (UserRole userRole : user.getUserRoles())
//			userRole.setUser(user);
		// rollback
		userRepo.save(user);

		List<UserRoleDTO> userRoleDTOs = userDTO.getUserRoles();
		for (UserRoleDTO userRoleDTO : userRoleDTOs) {
			if (userRoleDTO.getRole() != null) {
				// save to db
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(userRoleDTO.getRole());

				userRoleRepo.save(userRole);
			}
		}
	}

	@Transactional
	public void update(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		// convert dto -> entity
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());
		user.setBirthdate(userDTO.getBirthdate());
		user.setAvatar(userDTO.getAvatar());

		// rollback
		userRepo.save(user);
	}

	@Transactional
	@CachePut(cacheNames = "users", key = "#userDTO.id" )
	public UserDTO updatePassword(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		// convert dto -> entity
		user.setPassword(userDTO.getPassword());
		userRepo.save(user);
		return userDTO;
	}

	@Transactional
	@CacheEvict(cacheNames = "users", key = "#id" )
	public void delete(int id) {// delete by user role id
		userRepo.deleteById(id);
	}

	@Transactional
//	@CacheEvict(cacheNames = "users", allEntries = true )
	public void deleteAll(List<Integer> ids) {// delete by user role id
		userRepo.deleteAllById(ids);//
	}

	public PageDTO<UserDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);

		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : pageRS.getContent()) {
			userDTOs.add(new ModelMapper().map(user, UserDTO.class));
		}

		pageDTO.setContents(userDTOs);// set vao pagedto
		return pageDTO;
	}

	@Cacheable(cacheNames = "users", key = "#id", unless = "#result == null")
	public UserDTO getById(int id) { // java8, optinal
		System.out.println("NO CACHE");
		User user = userRepo.findById(id).orElseThrow(NoResultException::new);// java8 lambda

		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		// modelmapper
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setUsername(user.getUsername());
//		userDTO.setBirthdate(user.getBirthdate());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAvatar(user.getAvatar());
//		userDTO.setCreatedAt(user.getCreatedAt());

		return userDTO;
	}
	
}
