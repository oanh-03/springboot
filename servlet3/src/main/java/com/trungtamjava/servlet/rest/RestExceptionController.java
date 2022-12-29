package com.trungtamjava.servlet.rest;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trungtamjava.servlet.dto.ResponseDTO;

@RestControllerAdvice(basePackages = "com.trungtamjava.servlet.rest")
public class RestExceptionController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler({ NoResultException.class })
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseDTO<Void> noResult(NoResultException ex) {
		logger.info("sql ex: ", ex);
		return ResponseDTO.<Void>builder().status(404).error("Not Found").build();// view
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseDTO<Void> badInput(MethodArgumentNotValidException ex) {
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();

		String msg = "";
		for (ObjectError e : errors) {
			FieldError fieldError = (FieldError) e;

			msg += fieldError.getField() + ":" + e.getDefaultMessage() + ";";
		}

		return ResponseDTO.<Void>builder().status(400).error(msg).build();// view
	}

	@ExceptionHandler({ Exception.class })
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDTO<Void> exception(Exception ex) {
		logger.error("ex: ", ex);
		return ResponseDTO.<Void>builder().status(500).error("SERVER ERROR").build();// view
	}
}
