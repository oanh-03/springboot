package com.trungtamjava.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trungtamjava.servlet.Repo.PersonRepo;
import com.trungtamjava.servlet.entity.Person;

@Service
public class PersonService {

	@Autowired
	PersonRepo personRe;
	
	public List<Person> getAll(){
		return personRe.findAll();
	}
}
