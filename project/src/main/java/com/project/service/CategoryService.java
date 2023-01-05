package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Repo.CategoryRepo;
import com.project.dto.CategoryDTO;
import com.project.dto.PageDTO;
import com.project.entity.Category;
@Service

public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	@Transactional
	@CacheEvict(cacheNames = "category-search", allEntries = true)
	public void create(CategoryDTO categoryDTO) {
		Category category = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(category);
		
		//tra ve idsau khi tao
		categoryDTO.setId(category.getId());
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "categories", key = "#categoryDTO.id"),
			@CacheEvict(cacheNames = "category-search", allEntries = true)
	})
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);
		category.setName(categoryDTO.getName());
		categoryRepo.save(category);
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(cacheNames = "categories", key = "#id"),
			@CacheEvict(cacheNames = "category-search", allEntries = true)
	})
	public void delete(int id) {
		categoryRepo.deleteById(id);
	}

	@Cacheable(cacheNames = "category-search")
	public PageDTO<CategoryDTO> searchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Category> pageRS = categoryRepo.searchByName("%" + name + "%", pageable);

		PageDTO<CategoryDTO> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(pageRS.getTotalPages());
		pageDTO.setTotalElements(pageRS.getTotalElements());

		// java 8 : lambda, stream
		List<CategoryDTO> categoryDTOs = pageRS.get()
				.map(category -> new ModelMapper().map(category, CategoryDTO.class)).collect(Collectors.toList());

		pageDTO.setContents(categoryDTOs);// set vao pagedto
		return pageDTO;
	}

	@Cacheable(cacheNames = "categories", key = "#id", unless = "#result == null")
	public CategoryDTO getById(int id) { // java8, optinal
		Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);// java8 lambda
		return new ModelMapper().map(category, CategoryDTO.class);
	}

}
