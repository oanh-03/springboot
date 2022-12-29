package com.trungtamjava.servlet.rest;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.trungtamjava.servlet.dto.ResponseDTO;
import com.trungtamjava.servlet.dto.UserDTO;
import com.trungtamjava.servlet.dto.PageDTO;



@RestController
@RequestMapping("/api/cache")
public class RestCacheController {
	@Autowired
	CacheManager cacheManager;

	@GetMapping("/")
	public List<String> getCache() {
		/// RestTemplate: GOI API CUA server khac de check du lieu
		// Webclient
		RestTemplate restTemplate = new RestTemplate();

		ResponseDTO<PageDTO<UserDTO>> responseDTO = 
				restTemplate.getForObject("http://localhost:8082/api/user/1",
				ResponseDTO.class)
				;
		
		System.out.println(responseDTO.getStatus());
		
		List<String> cacheNames = cacheManager.getCacheNames().stream().collect(Collectors.toList());

		return cacheNames;
	}

	@DeleteMapping("/{name}")
	public void deleteCache(@PathVariable("name") String name) {
		Cache cache = cacheManager.getCache(name);
		if (cache != null)
			cache.clear();
	}
}
