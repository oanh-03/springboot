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

import com.trungtamjava.servlet.Repo.GroupRepo;
import com.trungtamjava.servlet.Repo.UserRepo;
import com.trungtamjava.servlet.Repo.UserRoleRepo;
import com.trungtamjava.servlet.dto.GroupDTO;
import com.trungtamjava.servlet.dto.PageDTO;
import com.trungtamjava.servlet.dto.UserDTO ;
import com.trungtamjava.servlet.dto.UserRoleDTO;
import com.trungtamjava.servlet.entity.Group;
import com.trungtamjava.servlet.entity.User;
import com.trungtamjava.servlet.entity.UserRole;

@Service
public class GroupService {
	@Autowired
	GroupRepo groupRepo;
	
	@Autowired
	UserRepo userRepo ;
	
	@Transactional
	public void create(GroupDTO groupDTO) {
		Group group = new Group();
				group.setName(groupDTO.getName());
		List<User> users = new ArrayList<>();
		for (UserDTO userDTO : groupDTO.getUsers()) {
			
				User user = userRepo.findById(userDTO.getId())
						.orElseThrow(NoResultException::new);
				users.add(user);
				
			}
		group.setUsers(users);
		groupRepo.save(group);
		}

	@Transactional
	public void update(GroupDTO groupDTO) {
		Group group = groupRepo.findById(groupDTO.getId()).orElseThrow(
				NoResultException::new);
		// convert dto -> entity
		group.setName(groupDTO.getName());
		if(group.getUsers()!=null) {
			group.getUsers().clear();
			for (UserDTO x : groupDTO.getUsers()) {
				User user = userRepo.findById(x.getId()).orElseThrow(NoResultException::new);
				group.getUsers().add(user);
			}
		}else {
			List<User> users = new ArrayList<>();
			for (UserDTO x : groupDTO.getUsers()) {
				User user = userRepo.findById(x.getId()).orElseThrow(NoResultException::new);
				users.add(user);
			}
			group.setUsers(users);
		}
		// rollback
		groupRepo.save(group);
	}
	@Transactional
	public void delete(int id) {// delete by user role id
		groupRepo.deleteById(id);
	}

	@Transactional
	public void deleteAll(List<Integer> ids) {// delete by user role id
		groupRepo.deleteAllById(ids);//
	}
	public PageDTO<GroupDTO> searchByName(String name,int page,int size){
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Group> pageRS = groupRepo.searchByName("%" + name + "%", pageable);
		PageDTO<GroupDTO>  pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());
		List<GroupDTO> groupDTOs = new ArrayList<>();
		for (Group x : pageRS.getContent()) {
			groupDTOs.add(new ModelMapper()
					.map(x, GroupDTO.class));
		}
		pageDTO.setContents(groupDTOs);
		return pageDTO;
	}
	
	
	
	public GroupDTO getById(int id) {
		Group group = groupRepo.findById(id)
				.orElseThrow(NoResultException::new);
		
		GroupDTO groupDTO = new ModelMapper()
				.map(group, GroupDTO.class);
		return groupDTO;
	}
	
}
