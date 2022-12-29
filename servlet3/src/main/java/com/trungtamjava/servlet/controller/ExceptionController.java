package com.trungtamjava.servlet.controller;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.trungtamjava.servlet.controller")
public class ExceptionController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	//bat cac loai exception tai day
	@ExceptionHandler({NoResultException.class})
	public String noResult(NoResultException ex) {
		logger.error("sql ex:",ex);
		return "404.html";//view
	}
	@ExceptionHandler({Exception.class})
	public String exception(SQLException ex) {
		logger.error("sql ex:",ex);
		return "exception.html";//view
	}
	
}
