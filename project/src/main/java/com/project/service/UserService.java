package com.project.service;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Repo.UserRepo;
import com.project.dto.PageDTO;
import com.project.dto.UserDTO;
import com.project.entity.User;



@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Transactional
	public void create(UserDTO userDTO) {
		User user = new ModelMapper().map(userDTO, User.class);
		userRepo.save(user);

		// tra ve idsau khi tao
		userDTO.setId(user.getId());
	}

	@Transactional
	public void update(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setName(userDTO.getName());
		user.setBirthdate(userDTO.getBirthdate());
		user.setRoles(userDTO.getRoles());
		user.setEmail(userDTO.getEmail());

		if (userDTO.getAvatar() != null)
			user.setAvatar(userDTO.getAvatar());

		userRepo.save(user);
	}

	@Transactional
	public void update2(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);

		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(UserDTO.class, User.class).addMappings(map -> {
			map.skip(User::setPassword);
			if (userDTO.getAvatar() == null)
				map.skip(User::setAvatar);
		}).setProvider(p -> user);

		User saveUser = mapper.map(userDTO, User.class);

		userRepo.save(saveUser);
	}

	@Transactional
	public void updatePassword(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setPassword(userDTO.getPassword());

		userRepo.save(user);
	}

	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	public PageDTO<UserDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);

		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		// java 8 : lambda, stream
		List<UserDTO> userDTOs = pageRS.get().map(user -> new ModelMapper().map(user, UserDTO.class))
				.collect(Collectors.toList());

		pageDTO.setContents(userDTOs);// set vao pagedto
		return pageDTO;
	}

	public UserDTO getById(int id) { // java8, optinal
		User user = userRepo.findById(id).orElseThrow(NoResultException::new);// java8 lambda
		return new ModelMapper().map(user, UserDTO.class);
	}

}

