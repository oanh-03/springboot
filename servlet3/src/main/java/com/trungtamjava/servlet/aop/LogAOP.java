package com.trungtamjava.servlet.aop;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAOP {
	@Autowired
	CacheManager cacheManager;

	@Before("execution(* jmaster.io.project2.service.UserService.getById(*))")
	public void create(JoinPoint joinPoint) {
		int id = (Integer) joinPoint.getArgs()[0];
		log.info("HELLLLLLOOOOOOOO " + id);
	}
	
	@After("execution(* jmaster.io.project2.service.UserService.deleteAll(*))")
	public void deleteAll(JoinPoint joinPoint) {
		List<Integer> ids = (List<Integer>) joinPoint.getArgs()[0];
		log.info("DELETE ALL " + ids.toString());
		for (int id : ids) {
			cacheManager.getCache("users").evict(id);
		}
	}
}
